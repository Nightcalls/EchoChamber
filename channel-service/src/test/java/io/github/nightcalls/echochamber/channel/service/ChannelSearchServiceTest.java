package io.github.nightcalls.echochamber.channel.service;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.ChannelName;
import io.github.nightcalls.echochamber.channel.ChannelOwner;
import io.github.nightcalls.echochamber.channel.repository.ChannelRepository;
import io.github.nightcalls.echochamber.util.DbTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.Optional;

class ChannelSearchServiceTest extends DbTestBase {
    private static final long CHANNEL_ID = 111;
    private static final long NON_EXISTING_CHANNEL_ID = 112;
    private static final String CHANNEL_NAME = "test111";
    private static final String NON_EXISTING_CHANNEL_NAME = "test112";

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelSearchService channelSearchService;

    @BeforeEach
    public void beforeEach() {
        channelRepository.insertChannel(new Channel(
                CHANNEL_ID,
                new ChannelName(CHANNEL_NAME),
                ChannelOwner.createChannelOwnerFromRawUserId(111111),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                false
        ));
    }

    @AfterEach
    public void afterEach() {
        channelRepository.truncate();
    }

    @Test
    void searchForExistingChannel() {
        Assertions.assertDoesNotThrow(() -> channelSearchService.getChannel(CHANNEL_ID).orElseThrow());
        Channel channel = channelSearchService.getChannel(CHANNEL_ID).orElseThrow();
        Assertions.assertEquals(channel.getId(), CHANNEL_ID);
        Assertions.assertDoesNotThrow(() -> channelSearchService.getChannelByName(CHANNEL_NAME).orElseThrow());
        Channel channel2 = channelSearchService.getChannelByName(CHANNEL_NAME).orElseThrow();
        Assertions.assertEquals(channel2.getName().getName(), CHANNEL_NAME);
    }

    @Test
    void searchForNonExistingChannel() {
        Optional<Channel> channel = channelSearchService.getChannel(NON_EXISTING_CHANNEL_ID);
        Assertions.assertFalse(channel.isPresent());
        Optional<Channel> channel2 = channelSearchService.getChannelByName(NON_EXISTING_CHANNEL_NAME);
        Assertions.assertFalse(channel2.isPresent());
    }
}