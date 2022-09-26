package io.github.nightcalls.echochamber.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserNameTest {
    @Test
    public void checksForNameNull() {
        Assertions.assertDoesNotThrow(() -> new UserName("legal"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName(null));
    }

    @Test
    public void checkForNameLength() {
        Assertions.assertDoesNotThrow(() -> new UserName("legal"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName(""));
        Assertions.assertDoesNotThrow(() -> new UserName("a".repeat(UserName.MIN_NAME_LENGTH)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName("a".repeat(UserName.MIN_NAME_LENGTH - 1)));
        Assertions.assertDoesNotThrow(() -> new UserName("a".repeat(UserName.MAX_NAME_LENGTH)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName("a".repeat(UserName.MAX_NAME_LENGTH + 1)));
    }

    @Test
    public void checkForIllegalCharacters() {
        Assertions.assertDoesNotThrow(() -> new UserName("legal"));
        for (char c : UserName.ILLEGAL_CHARACTERS) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName("legal" + c));
        }
    }
}