package io.github.nightcalls.echochamber.user.service.create;

import io.github.nightcalls.echochamber.user.repository.UserRepository;
import io.github.nightcalls.echochamber.util.DbTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// TODO UserCreateService test when authorization will be ready
public class UserCreateServiceTest extends DbTestBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCreateService userCreateService;

    @BeforeEach
    public void beforeEach() {
    }

    @AfterEach
    public void afterEach() {
        userRepository.truncate();
    }

    @Test
    void createUser() {
    }

    @Test
    void createExistingUser() {
    }

}
