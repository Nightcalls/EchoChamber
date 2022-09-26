package io.github.nightcalls.echochamber.channel.service.create;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.ChannelOwner;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.nightcalls.echochamber.user.api.grpc.UserApiService;

import java.util.Optional;

@Service
public class ChannelCreateService {
    private static final Logger log = LoggerFactory.getLogger(ChannelCreateService.class);

    private final ChannelRepository channelRepository;
    private final UserApiService userApiService;

    @Autowired
    public ChannelCreateService(ChannelRepository channelRepository, UserApiService userApiService) {
        this.channelRepository = channelRepository;
        this.userApiService = userApiService;
    }

    public void createChannel(String name, long ownerId) throws ChannelCreationException {
        Optional<UserApi.User> owner = userApiService.getUser(ownerId);

        if (owner.isEmpty()) {
            throw new ChannelCreationException("User " + ownerId + " not found and can't create a channel");
        }

        if (owner.get().getDeleted()) {
            throw new ChannelCreationException("Deleted user " + ownerId + " can't create a channel");
        }

        long channelId = channelRepository.generateNewChannelId();
        Channel channel = new Channel
                .Builder()
                .id(channelId)
                .name(name)
                .owner(new ChannelOwner(owner.get()))
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
