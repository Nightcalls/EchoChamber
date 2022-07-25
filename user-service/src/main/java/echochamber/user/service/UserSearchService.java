package echochamber.user.service;

import echochamber.user.User;
import echochamber.user.repository.UserRepository;
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

    public Optional<User> getUser(long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getUsers(Collection<Long> ids) {
        return userRepository.findUsersByIds(ids);
    }
}
