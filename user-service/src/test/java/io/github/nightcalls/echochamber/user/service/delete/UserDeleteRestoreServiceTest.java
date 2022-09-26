package io.github.nightcalls.echochamber.user.service.delete;

import io.github.nightcalls.echochamber.user.service.NoSuchUserException;
import io.github.nightcalls.echochamber.util.DbTestBase;
import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDeleteRestoreServiceTest extends DbTestBase {
    private static final long USER_ID = 111;
    private static final long DELETED_USER_ID = 112;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDeleteRestoreService userDeleteRestoreService;

    @BeforeEach
    public void beforeEach() {
        var userNormal = new User.Builder()
                .id(USER_ID)
                .name("test")
                .lastLoginInfo("localhost")
                .build();
        var userDeleted = new User.Builder(userNormal)
                .id(DELETED_USER_ID)
                .deleted(true)
                .build();
        userRepository.insertUsers(List.of(userNormal, userDeleted));
    }

    @AfterEach
    public void afterEach() {
        userRepository.truncate();
    }

    @Test
    public void missingUserForDeletion() {
        Assertions.assertDoesNotThrow(() -> userDeleteRestoreService.deleteUser(USER_ID));
        Assertions.assertThrows(NoSuchUserException.class, () -> userDeleteRestoreService.deleteUser(1337));
    }

    @Test
    public void userAlreadyDeleted() {
        var before = userRepository.findUserById(DELETED_USER_ID);
        userDeleteRestoreService.deleteUser(DELETED_USER_ID);
        var after = userRepository.findUserById(DELETED_USER_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    public void deletionUpdatesTs() {
        var before = userRepository.findUserById(USER_ID).orElseThrow();
        userDeleteRestoreService.deleteUser(USER_ID);
        var after = userRepository.findUserById(USER_ID).orElseThrow();
        assertThat(before.getUpdatedTs()).isBefore(after.getUpdatedTs());
    }

    @Test
    public void missingUserForRestoration() {
        Assertions.assertDoesNotThrow(() -> userDeleteRestoreService.restoreUser(DELETED_USER_ID));
        Assertions.assertThrows(NoSuchUserException.class, () -> userDeleteRestoreService.restoreUser(1337));
    }

    @Test
    public void restoringNonDeletedUser() {
        var before = userRepository.findUserById(USER_ID);
        userDeleteRestoreService.restoreUser(USER_ID);
        var after = userRepository.findUserById(USER_ID);
        Assertions.assertEquals(before, after);
    }

    @Test
    public void restorationUpdatesTs() {
        userDeleteRestoreService.deleteUser(DELETED_USER_ID);
        var before = userRepository.findUserById(DELETED_USER_ID).orElseThrow();
        userDeleteRestoreService.restoreUser(DELETED_USER_ID);
        var after = userRepository.findUserById(DELETED_USER_ID).orElseThrow();
        assertThat(before.getUpdatedTs()).isBefore(after.getUpdatedTs());
    }
}