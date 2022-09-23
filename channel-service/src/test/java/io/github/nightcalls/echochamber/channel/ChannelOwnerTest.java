package io.github.nightcalls.echochamber.channel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChannelOwnerTest {

    @Test
    void checkForPositiveId() {
        Assertions.assertDoesNotThrow(() -> ChannelOwner.createChannelOwnerFromRawUserId(113));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ChannelOwner.createChannelOwnerFromRawUserId(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ChannelOwner.createChannelOwnerFromRawUserId(-5));
    }
}