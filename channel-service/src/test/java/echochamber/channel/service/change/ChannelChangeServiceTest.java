package echochamber.channel.service.change;

import echochamber.DbTestBase;
import echochamber.channel.Channel;
import echochamber.channel.ChannelName;
import echochamber.channel.ChannelOwner;
import echochamber.channel.api.grpc.ChannelForUserApi;
import echochamber.channel.repository.ChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;

class ChannelChangeServiceTest extends DbTestBase {
    private static final long CHANNEL_ID = 111;
    private static final long USER_ID = 1;

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelChangeService channelChangeService;

    @BeforeEach
    void beforeEach() {
        channelRepository.insertChannel(new Channel(
                CHANNEL_ID,
                new ChannelName("test"),
                new ChannelOwner(ChannelForUserApi.grpcUserResponse(USER_ID)),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false
        ));
    }

    @AfterEach
    void afterEach() {
        channelRepository.truncate();
    }

    @Test
    void noChanges() {
        var before = channelRepository.findChannelById(CHANNEL_ID);
        channelChangeService.changeChannel(CHANNEL_ID, new ChannelChanges(null));
        var after = channelRepository.findChannelById(CHANNEL_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    void nullChanges() {
        var before = channelRepository.findChannelById(CHANNEL_ID);
        channelChangeService.changeChannel(CHANNEL_ID, new ChannelChanges(null));
        var after = channelRepository.findChannelById(CHANNEL_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    void invalidChannelName() {
        Assertions.assertDoesNotThrow(() -> channelChangeService
                .changeChannel(CHANNEL_ID, new ChannelChanges("legal")));
        Assertions.assertThrows(ChannelChangeService.InvalidChangeException.class,
                () -> channelChangeService.changeChannel(CHANNEL_ID, new ChannelChanges("not")));
    }

    @Test
    void missingChannel() {
        Assertions.assertDoesNotThrow(() -> channelChangeService
                .changeChannel(CHANNEL_ID, new ChannelChanges("legal")));
        Assertions.assertThrows(ChannelChangeService.NoSuchChannelException.class, () -> channelChangeService
                .changeChannel(CHANNEL_ID + 1, new ChannelChanges("legal")));
    }
}