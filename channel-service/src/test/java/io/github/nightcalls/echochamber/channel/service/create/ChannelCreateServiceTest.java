package io.github.nightcalls.echochamber.channel.service.create;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.ChannelName;
import io.github.nightcalls.echochamber.channel.ChannelOwner;
import io.github.nightcalls.echochamber.channel.api.v1.CreateChannelRequest;
import io.github.nightcalls.echochamber.channel.config.ChannelCreateServiceTestConfiguration;
import io.github.nightcalls.echochamber.channel.grpc.UserApiService;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.channel.user_api_service.UserApiServiceMock;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi;
import io.github.nightcalls.echochamber.util.DbTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.OffsetDateTime;

@ContextConfiguration(classes = ChannelCreateServiceTestConfiguration.class)
class ChannelCreateServiceTest extends DbTestBase {
    private static final long CHANNEL_ID = 111;
    private static final String CHANNEL_NAME = "test111";
    private static final String NON_EXISTING_CHANNEL_NAME = "test112";

    private UserApiServiceMock mock;

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelCreateService channelCreateService;

    @BeforeEach
    public void beforeEach() {
        mock = (UserApiServiceMock) userApiService;
        mock.addUser(UserApi.User.newBuilder().setId(211).build());
        mock.addUser(UserApi.User.newBuilder().setId(212).setDeleted(true).build());

        channelRepository.insertChannel(new Channel(
                CHANNEL_ID,
                new ChannelName(CHANNEL_NAME),
                new ChannelOwner(211),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false
        ));
    }

    @AfterEach
    public void afterEach() {
        mock.removeUser(211);
        mock.removeUser(212);

        channelRepository.truncate();
    }

    @Test
    public void createChannel() {
        var before = channelRepository.findChannelByName(CHANNEL_NAME);

        CreateChannelRequest createChannelRequest = new CreateChannelRequest(NON_EXISTING_CHANNEL_NAME, 211);
        Assertions.assertDoesNotThrow(() -> channelCreateService.createChannel(createChannelRequest));

        var after = channelRepository.findChannelByName(NON_EXISTING_CHANNEL_NAME);
        Assertions.assertNotEquals(before, after);
    }

    @Test
    public void createChannelForNonExistingUser() {
        CreateChannelRequest createChannelRequest2 = new CreateChannelRequest(NON_EXISTING_CHANNEL_NAME, 213);
        Assertions.assertThrows(ChannelCreateService.ChannelCreationException.class, () -> channelCreateService.createChannel(createChannelRequest2));
    }

    @Test
    public void createChannelForDeletedUser() {
        CreateChannelRequest createChannelRequest = new CreateChannelRequest(NON_EXISTING_CHANNEL_NAME, 212);
        Assertions.assertThrows(ChannelCreateService.ChannelCreationException.class, () -> channelCreateService.createChannel(createChannelRequest));
    }
}