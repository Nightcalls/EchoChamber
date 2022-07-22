package echochamber.user.service.delete;

import echochamber.user.User;
import echochamber.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserDeleteRestoreService {
    private static final Logger log = LoggerFactory.getLogger(UserDeleteRestoreService.class);

    private final UserRepository userRepository;

    public UserDeleteRestoreService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteUser(long userId) {
        User user = userRepository.findUserByIdForUpdate(userId)
                .orElseThrow(() -> new UserDeleteRestoreService.NoSuchUserException(userId));
        if (user.isDeleted()) {
            log.warn("Tried to delete user {} but user is already deleted", userId);
            return;
        }
        user.setDeleted(true);
        user.setUpdatedTs(OffsetDateTime.now());
        userRepository.updateUser(user);
        log.info("Deleted user {}", userId);
    }

    @Transactional
    public void restoreUser(long userId) {
        User user = userRepository.findUserByIdForUpdate(userId)
                .orElseThrow(() -> new UserDeleteRestoreService.NoSuchUserException(userId));
        if (!user.isDeleted()) {
            log.warn("Tried to restore user {} but user is not deleted", userId);
            return;
        }
        user.setDeleted(false);
        user.setUpdatedTs(OffsetDateTime.now());
        userRepository.updateUser(user);
        log.info("Restored user {}", userId);
    }

    public static class NoSuchUserException extends RuntimeException {
        public NoSuchUserException(long userId) {
            super("User with ID " + userId + " doesn't exist");
        }
    }
}
