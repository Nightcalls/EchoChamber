package io.github.nightcalls.echochamber.channel.service;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelSearchService {
    private static final Logger log = LoggerFactory.getLogger(ChannelSearchService.class);

    private final ChannelRepository channelRepository;

    public ChannelSearchService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Optional<Channel> getChannel(long channelId) {
        return channelRepository.findChannelById(channelId);
    }

    public List<Channel> getChannels(Collection<Long> channelIds) {
        return channelRepository.findChannelsByIds(channelIds);
    }
}
