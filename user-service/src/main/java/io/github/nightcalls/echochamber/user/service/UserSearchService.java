package io.github.nightcalls.echochamber.user.service;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserSearchService {
    private static final Logger log = LoggerFactory.getLogger(UserSearchService.class);

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(long userId) {
        return userRepository.findUserById(userId);
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    public List<User> getUsers(Collection<Long> userIds) {
        return userRepository.findUsersByIds(userIds);
    }
}
