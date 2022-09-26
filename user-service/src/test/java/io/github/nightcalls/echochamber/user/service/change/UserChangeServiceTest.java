package io.github.nightcalls.echochamber.user.service.change;

import io.github.nightcalls.echochamber.user.service.NoSuchUserException;
import io.github.nightcalls.echochamber.util.DbTestBase;
import io.github.nightcalls.echochamber.user.LastLoginInfo;
import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.UserName;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;

class UserChangeServiceTest extends DbTestBase {
    private static final long USER_ID = 111;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserChangeService userChangeService;

    @BeforeEach
    public void beforeEach() {
        userRepository.insertUser(new User(
                USER_ID,
                new UserName("test"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                null,
                new LastLoginInfo(OffsetDateTime.now(), "127.0.0.1"),
                false
        ));
    }

    @AfterEach
    public void afterEach() {
        userRepository.truncate();
    }

    @Test
    public void noChanges() {
        var before = userRepository.findUserById(USER_ID);
        userChangeService.changeUser(USER_ID, new UserChanges(null));
        var after = userRepository.findUserById(USER_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    public void nullChanges() {
        var before = userRepository.findUserById(USER_ID);
        userChangeService.changeUser(USER_ID, new UserChanges(null));
        var after = userRepository.findUserById(USER_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    public void invalidUsername() {
        Assertions.assertDoesNotThrow(() -> userChangeService.changeUser(USER_ID, new UserChanges("legal")));
        Assertions.assertThrows(UserChangeService.InvalidChangeException.class, () ->
                userChangeService.changeUser(USER_ID, new UserChanges("not legal")));
    }

    @Test
    public void missingUser() {
        Assertions.assertDoesNotThrow(() -> userChangeService.changeUser(USER_ID, new UserChanges("legal")));
        Assertions.assertThrows(NoSuchUserException.class, () ->
                userChangeService.changeUser(123, new UserChanges("legal")));
    }
}