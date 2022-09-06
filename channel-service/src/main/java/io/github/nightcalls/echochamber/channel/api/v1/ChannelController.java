package io.github.nightcalls.echochamber.channel.api.v1;

import io.github.nightcalls.echochamber.channel.service.ChannelSearchService;
import io.github.nightcalls.echochamber.channel.service.change.ChannelChangeService;
import io.github.nightcalls.echochamber.channel.service.change.ChannelChanges;
import io.github.nightcalls.echochamber.channel.service.create.ChannelCreateService;
import io.github.nightcalls.echochamber.channel.service.create.CreateChannelRequest;
import io.github.nightcalls.echochamber.channel.service.delete.ChannelDeleteRestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/channel")
public class ChannelController {
    private final ChannelSearchService channelSearchService;
    private final ChannelCreateService channelCreateService;
    private final ChannelChangeService channelChangeService;
    private final ChannelDeleteRestoreService channelDeleteRestoreService;

    public ChannelController(ChannelSearchService channelSearchService, ChannelChangeService channelChangeService,
                             ChannelCreateService channelCreateService,
                             ChannelDeleteRestoreService channelDeleteRestoreService) {
        this.channelSearchService = channelSearchService;
        this.channelCreateService = channelCreateService;
        this.channelChangeService = channelChangeService;
        this.channelDeleteRestoreService = channelDeleteRestoreService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable("id") long id) {
        var channel = channelSearchService.getChannel(id);
        if (channel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var channelDto = ChannelDto.fromChannel(channel.get());
        return ResponseEntity.ok(channelDto);
    }

    @PostMapping("create")
    public ResponseEntity<?> createChannel(@RequestBody CreateChannelRequest createChannelRequest) {
        try {
            channelCreateService.createChannel(createChannelRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editChannel(@PathVariable("id") long id, @RequestBody ChannelChanges changes) {
        try {
            var changedChannel = channelChangeService.changeChannel(id, changes);
            return ResponseEntity.ok(ChannelDto.fromChannel(changedChannel));
        } catch (ChannelChangeService.NoSuchChannelException e) {
            return ResponseEntity.notFound().build();
        } catch (ChannelChangeService.InvalidChangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteChannel(@PathVariable("id") long id) {
        try {
            channelDeleteRestoreService.deleteChannel(id);
            return ResponseEntity.noContent().build();
        } catch (ChannelDeleteRestoreService.NoSuchChannelException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/restore")
    public ResponseEntity<?> restoreChannel(@PathVariable("id") long id) {
        try {
            channelDeleteRestoreService.restoreChannel(id);
            return ResponseEntity.noContent().build();
        } catch (ChannelDeleteRestoreService.NoSuchChannelException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
