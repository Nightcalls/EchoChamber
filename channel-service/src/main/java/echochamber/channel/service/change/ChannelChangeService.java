package echochamber.channel.service.change;

import echochamber.channel.Channel;
import echochamber.channel.ChannelName;
import echochamber.channel.repository.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Service
public class ChannelChangeService {
    private static final Logger log = LoggerFactory.getLogger(ChannelChangeService.class);

    private final ChannelRepository channelRepository;

    public ChannelChangeService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public Channel changeChannel(long channelId, ChannelChanges changes) {
        Channel channel = channelRepository.findChannelByIdForUpdate(channelId)
                .orElseThrow(() -> new NoSuchChannelException(channelId));
        if (!changes.hasChanges()) {
            log.warn("Tried to change channel {} but no actual changes exist", channelId);
            return channel;
        }

        var changesMsg = new ArrayList<String>();
        if (changes.isNameChanged()) {
            try {
                var newName = new ChannelName(changes.getName());
                changesMsg.add("name: " + channel.getName().getName() + " -> " + changes.getName());
                channel.setName(newName);
            } catch (Exception e) {
                throw new InvalidChangeException("Failed to set new channel name for channel "
                        + channelId + ": " + e.getMessage());
            }
        }

        channel.setUpdatedTs(OffsetDateTime.now());
        channelRepository.updateChannel(channel);
        log.info("Applied changes to channel {}: {}", channelId, changesMsg);
        return channel;
    }

    public static class NoSuchChannelException extends RuntimeException {
        public NoSuchChannelException(long channelId) {
            super("Channel with ID " + channelId + " doesn't exist");
        }
    }

    public static class InvalidChangeException extends RuntimeException {
        public InvalidChangeException(String msg) {
            super(msg);
        }
    }
}
