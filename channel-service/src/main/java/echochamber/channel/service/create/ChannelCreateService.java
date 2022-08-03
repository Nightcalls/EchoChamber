package echochamber.channel.service.create;

import echochamber.channel.Channel;
import echochamber.channel.repository.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChannelCreateService {
    private static final Logger log = LoggerFactory.getLogger(ChannelCreateService.class);

    private final ChannelRepository channelRepository;

    public ChannelCreateService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void createChannel(CreateChannelRequest createChannelRequest) {
        long channelId = channelRepository.generateNewChannelId();
        Channel channel = new Channel
                .Builder()
                .id(channelId)
                .name(createChannelRequest.getName())
                .owner(createChannelRequest.getUserId())
                .createdTs(createChannelRequest.getCreatedTs())
                .updatedTs(createChannelRequest.getUpdatedTs())
                .deleted(createChannelRequest.isDeleted())
                .build();
        channelRepository.insertChannel(channel);
    }
}
