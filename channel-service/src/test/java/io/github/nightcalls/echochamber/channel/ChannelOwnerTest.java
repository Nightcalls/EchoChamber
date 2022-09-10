package io.github.nightcalls.echochamber.channel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChannelOwnerTest {

    @Test
    void checkForPositiveId() {
        Assertions.assertDoesNotThrow(() -> new ChannelOwner(113));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelOwner(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelOwner(-5));
    }
}