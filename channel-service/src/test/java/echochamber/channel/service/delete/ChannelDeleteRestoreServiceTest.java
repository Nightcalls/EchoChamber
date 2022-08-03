package echochamber.channel.service.delete;

import echochamber.DbTestBase;
import echochamber.channel.Channel;
import echochamber.channel.ChannelOwner;
import echochamber.channel.api.grpc.ChannelForUserApi;
import echochamber.channel.repository.ChannelRepository;
import echochamber.channel.service.delete.ChannelDeleteRestoreService.NoSuchChannelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelDeleteRestoreServiceTest extends DbTestBase {
    private static final long CHANNEL_ID = 111;
    private static final long DELETED_CHANNEL_ID = 211;
    private static final long USER_ID = 1;

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelDeleteRestoreService channelDeleteRestoreService;

    @BeforeEach
    void beforeEach() {
        var channelNormal = new Channel.Builder().id(CHANNEL_ID).name("test").owner(new ChannelOwner(ChannelForUserApi.grpcUserResponse(USER_ID))).build();
        var channelDeleted = new Channel.Builder(channelNormal).id(DELETED_CHANNEL_ID).deleted(true).build();
        channelRepository.insertChannels(List.of(channelNormal, channelDeleted));
    }

    @AfterEach
    void afterEach() {
        channelRepository.truncate();
    }

    @Test
    void missingChannelForDeletion() {
        Assertions.assertDoesNotThrow(() -> channelDeleteRestoreService.deleteChannel(CHANNEL_ID));
        Assertions.assertThrows(NoSuchChannelException.class, () -> channelDeleteRestoreService.deleteChannel(CHANNEL_ID + 1));
    }

    @Test
    void channelAlreadyDeleted() {
        var before = channelRepository.findChannelById(DELETED_CHANNEL_ID);
        channelDeleteRestoreService.deleteChannel(DELETED_CHANNEL_ID);
        var after = channelRepository.findChannelById(DELETED_CHANNEL_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    void deletionUpdatedTs() {
        var before = channelRepository.findChannelById(CHANNEL_ID).orElseThrow();
        channelDeleteRestoreService.deleteChannel(CHANNEL_ID);
        var after = channelRepository.findChannelById(CHANNEL_ID).orElseThrow();
        assertThat(before.getUpdatedTs()).isBefore(after.getUpdatedTs());
    }

    @Test
    void missingChannelForRestoration() {
        Assertions.assertDoesNotThrow(() -> channelDeleteRestoreService.restoreChannel(DELETED_CHANNEL_ID));
        Assertions.assertThrows(NoSuchChannelException.class, () -> channelDeleteRestoreService.deleteChannel(DELETED_CHANNEL_ID + 1));
    }

    @Test
    void restoringNonDeletedChannel() {
        var before = channelRepository.findChannelById(CHANNEL_ID);
        channelDeleteRestoreService.restoreChannel(CHANNEL_ID);
        var after = channelRepository.findChannelById(CHANNEL_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    void restorationUpdatedTs() {
        channelDeleteRestoreService.deleteChannel(DELETED_CHANNEL_ID);
        var before = channelRepository.findChannelById(DELETED_CHANNEL_ID).orElseThrow();
        channelDeleteRestoreService.restoreChannel(DELETED_CHANNEL_ID);
        var after = channelRepository.findChannelById(DELETED_CHANNEL_ID).orElseThrow();
        assertThat(before.getUpdatedTs()).isBefore(after.getUpdatedTs());
    }
}