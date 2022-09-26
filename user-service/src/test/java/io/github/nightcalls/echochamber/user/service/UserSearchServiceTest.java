package io.github.nightcalls.echochamber.user.service;

import io.github.nightcalls.echochamber.user.LastLoginInfo;
import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.UserName;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import io.github.nightcalls.echochamber.util.DbTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.Optional;

public class UserSearchServiceTest extends DbTestBase {
    private static final long USER_ID = 111;
    private static final long NON_EXISTING_USER_ID = 112;
    private static final String USER_NAME = "test111";
    private static final String NON_EXISTING_USER_NAME = "test112";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSearchService userSearchService;

    @BeforeEach
    public void beforeEach() {
        userRepository.insertUser(new User(
                USER_ID,
                new UserName(USER_NAME),
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
    void searchForExistingUser() {
        Assertions.assertDoesNotThrow(() -> userSearchService.getUser(USER_ID).orElseThrow());
        User user = userSearchService.getUser(USER_ID).orElseThrow();
        Assertions.assertEquals(user.getId(), USER_ID);
        Assertions.assertDoesNotThrow(() -> userSearchService.getUserByName(USER_NAME).orElseThrow());
        User user2 = userSearchService.getUserByName(USER_NAME).orElseThrow();
        Assertions.assertEquals(user2.getName().getName(), USER_NAME);
    }

    @Test
    void searchForNonExistingUser() {
        Optional<User> user = userSearchService.getUser(NON_EXISTING_USER_ID);
        Assertions.assertFalse(user.isPresent());
        Optional<User> user2 = userSearchService.getUserByName(NON_EXISTING_USER_NAME);
        Assertions.assertFalse(user2.isPresent());
    }
}
