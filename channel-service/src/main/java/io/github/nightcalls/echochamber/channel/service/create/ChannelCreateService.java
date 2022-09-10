package io.github.nightcalls.echochamber.channel.service.create;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.api.v1.CreateChannelRequest;
import io.github.nightcalls.echochamber.channel.grpc.UserApiService;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelCreateService {
    private static final Logger log = LoggerFactory.getLogger(ChannelCreateService.class);

    private final ChannelRepository channelRepository;
    private final UserApiService stub;

    @Autowired
    public ChannelCreateService(ChannelRepository channelRepository, UserApiService userApiService) {
        this.channelRepository = channelRepository;
        this.stub = userApiService;
    }

    public void createChannel(CreateChannelRequest createChannelRequest) throws ChannelCreationException {
        long ownerId = createChannelRequest.getOwnerId();
        UserApi.User response = stub.getUser(ownerId);

        if (response == null) {
            throw new ChannelCreationException("User " + ownerId + " not found and can't create a channel");
        }

        if (response.getDeleted()) {
            throw new ChannelCreationException("Deleted user " + ownerId + " can't create a channel");
        }

        long channelId = channelRepository.generateNewChannelId();
        Channel channel = new Channel
                .Builder()
                .id(channelId)
                .name(createChannelRequest.getName())
                .owner(ownerId)
                .build();
        channelRepository.insertChannel(channel);
    }

    static class ChannelCreationException extends RuntimeException {
        public ChannelCreationException(String message) {
            super(message);
        }

        public ChannelCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
