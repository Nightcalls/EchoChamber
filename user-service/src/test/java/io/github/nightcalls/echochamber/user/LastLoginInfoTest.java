package io.github.nightcalls.echochamber.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class LastLoginInfoTest {
    @Test
    public void checkForTimestampNull() {
        Assertions.assertDoesNotThrow(() -> new LastLoginInfo(OffsetDateTime.now(), "1.1.1.1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LastLoginInfo(null, "1.1.1.1"));
    }

    @Test
    public void checkForIpNull() {
        Assertions.assertDoesNotThrow(() -> new LastLoginInfo(OffsetDateTime.now(), "1.1.1.1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LastLoginInfo(OffsetDateTime.now(), null));
    }

    @Test
    public void checkIsIpBlank() {
        Assertions.assertDoesNotThrow(() -> new LastLoginInfo(OffsetDateTime.now(), "1.1.1.1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LastLoginInfo(OffsetDateTime.now(), ""));
    }
}