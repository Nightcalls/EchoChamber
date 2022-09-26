package io.github.nightcalls.echochamber.user.service.change;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.UserName;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import io.github.nightcalls.echochamber.user.service.NoSuchUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Service
public class UserChangeService {
    private static final Logger log = LoggerFactory.getLogger(UserChangeService.class);

    private final UserRepository userRepository;

    public UserChangeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User changeUser(long userId, UserChanges changes) {
        User user = userRepository.findUserByIdForUpdate(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));
        if (!changes.hasChanges()) {
            log.warn("Tried to change user {} but no actual changes exist", userId);
            return user;
        }

        var changesMsg = new ArrayList<String>();
        if (changes.isNameChanged()) {
            try {
                var newName = new UserName(changes.getName());
                changesMsg.add("name: " + user.getName().getName() + " -> " + changes.getName());
                user.setName(newName);
            } catch (Exception e) {
                throw new InvalidChangeException("Failed to set new username for user "
                        + userId + ": " + e.getMessage());
            }
        }

        user.setUpdatedTs(OffsetDateTime.now());
        userRepository.updateUser(user);
        log.info("Applied changes to user {}: {}", userId, changesMsg);
        return user;
    }

    public static class InvalidChangeException extends RuntimeException {
        public InvalidChangeException(String msg) {
            super(msg);
        }
    }
}
