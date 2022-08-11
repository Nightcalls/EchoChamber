package io.github.nightcalls.echochamber.user.service.create;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
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

    public void createUser(CreateUserRequest createUserRequest) {
        long userId = userRepository.generateNewUserId();
        User user = new User
                .Builder()
                .id(userId)
                .name(createUserRequest.getName())
                .createdTs(createUserRequest.getCreatedTs())
                .updatedTs(createUserRequest.getUpdatedTs())
                .discordAuthInfo(createUserRequest.getDiscordAuthInfo())
                .lastLoginInfo(createUserRequest.getIp())
                .deleted(createUserRequest.isDeleted())
                .build();
        userRepository.insertUser(user);
    }
}
