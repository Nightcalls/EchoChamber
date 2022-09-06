package io.github.nightcalls.echochamber.channel.service.create;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi;
import io.github.nightcalls.echochamber.user.api.grpc.UserApiServiceGrpc.UserApiServiceBlockingStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelCreateService {
    private static final Logger log = LoggerFactory.getLogger(ChannelCreateService.class);

    private final ChannelRepository channelRepository;
    private final UserApiServiceBlockingStub stub;

    @Autowired
    public ChannelCreateService(ChannelRepository channelRepository, UserApiServiceBlockingStub stub) {
        this.channelRepository = channelRepository;
        this.stub = stub;
    }

    public void createChannel(CreateChannelRequest createChannelRequest) {
        long ownerId = createChannelRequest.getOwnerId();
        UserApi.GetUserResponse response;
        try {
            response = stub.getUser(UserApi.GetUserRequest.newBuilder().setUserId(ownerId).build());
        } catch (Exception e) {
            throw new RuntimeException("User not found", e);
        }
        if (response.getUser().getDeleted()) {
            throw new RuntimeException("Non-existing user can not create a channel");
        }
        long channelId = channelRepository.generateNewChannelId();
        Channel channel = new Channel
                .Builder()
                .id(channelId)
                .name(createChannelRequest.getName())
                .owner(ownerId)
                // FIXME
                .createdTs(null)
                .updatedTs(null)
                .deleted(false)
                .build();
        channelRepository.insertChannel(channel);
    }
}
