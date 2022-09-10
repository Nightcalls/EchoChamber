package io.github.nightcalls.echochamber.channel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChannelNameTest {

    @Test
    void checksForNameNull() {
        Assertions.assertDoesNotThrow(() -> new ChannelName("legal"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelName(null));
    }

    @Test
    void checkForNameLength() {
        Assertions.assertDoesNotThrow(() -> new ChannelName("legal"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelName(""));
        Assertions.assertDoesNotThrow(() -> new ChannelName("a".repeat(ChannelName.MIN_NAME_LENGTH)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelName("a".repeat(ChannelName.MIN_NAME_LENGTH - 1)));
        Assertions.assertDoesNotThrow(() -> new ChannelName("a".repeat(ChannelName.MAX_NAME_LENGTH)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelName("a".repeat(ChannelName.MAX_NAME_LENGTH + 1)));
    }

    @Test
    void checkForIllegalCharacters() {
        Assertions.assertDoesNotThrow(() -> new ChannelName("legal"));
        for (char c : ChannelName.ILLEGAL_CHARACTERS) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new ChannelName("legal" + c));
        }
    }
}