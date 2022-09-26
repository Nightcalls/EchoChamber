package io.github.nightcalls.echochamber.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class AuthInfoTest {
    @Test
    public void checkEmptyOrNullId() {
        Assertions.assertDoesNotThrow(() -> new AuthInfo("12345", "12345", "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo(null, "12345", "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo(" ", "12345", "12345", OffsetDateTime.now()));
    }

    @Test
    public void checkEmptyOrNullToken() {
        Assertions.assertDoesNotThrow(() -> new AuthInfo("12345", "12345", "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo("12345", null, "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo("12345", "", "12345", OffsetDateTime.now()));
    }

    @Test
    public void checkEmptyOrNullRefreshToken() {
        Assertions.assertDoesNotThrow(() -> new AuthInfo("12345", "12345", "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo("12345", "12345", null, OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo("12345", "12345", "   ", OffsetDateTime.now()));
    }

    @Test
    public void checkEmptyOrNullRefreshedTs() {
        Assertions.assertDoesNotThrow(() -> new AuthInfo("12345", "12345", "12345", OffsetDateTime.now()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AuthInfo("12345", "12345", "12345", null));
    }

}