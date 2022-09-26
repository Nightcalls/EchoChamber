package io.github.nightcalls.echochamber.channel.service.delete;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.channel.service.NoSuchChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class ChannelDeleteRestoreService {
    private static final Logger log = LoggerFactory.getLogger(ChannelDeleteRestoreService.class);

    private final ChannelRepository channelRepository;

    public ChannelDeleteRestoreService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public void deleteChannel(long channelId) {
        Channel channel = channelRepository
                .findChannelByIdForUpdate(channelId)
                .orElseThrow(() -> new NoSuchChannelException(channelId));
        if (channel.isDeleted()) {
            log.warn("Tried to delete channel {} but channel is already delete", channelId);
            return;
        }
        channel.setDeleted(true);
        channel.setUpdatedTs(OffsetDateTime.now());
        channelRepository.updateChannel(channel);
        log.info("Deleted channel {}", channelId);
    }

    @Transactional
    public void restoreChannel(long channelId) {
        Channel channel = channelRepository
                .findChannelByIdForUpdate(channelId)
                .orElseThrow(() -> new NoSuchChannelException(channelId));
        if (!channel.isDeleted()) {
            log.warn("Tried to restore channel {} but channel is not deleted", channelId);
            return;
        }
        channel.setDeleted(false);
        channel.setUpdatedTs(OffsetDateTime.now());
        channelRepository.updateChannel(channel);
        log.info("Restored channel {}", channelId);
    }
}
