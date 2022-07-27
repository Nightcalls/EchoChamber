package echochamber.user.service.create;

import echochamber.user.User;
import echochamber.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserCreateService {
    private static final Logger log = LoggerFactory.getLogger(UserCreateService.class);

    private final UserRepository userRepository;

    public UserCreateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreation userCreation) {
        long userId = userRepository.generateNewUserId();
        User user = new User
                .Builder()
                .id(userId)
                .name(userCreation.getName())
                .createdTs(userCreation.getCreatedTs())
                .updatedTs(userCreation.getUpdatedTs())
                .discordAuthInfo(userCreation.getDiscordAuthInfo())
                .lastLoginInfo(userCreation.getIp())
                .deleted(userCreation.isDeleted())
                .build();
        userRepository.insertUser(user);
    }
}
