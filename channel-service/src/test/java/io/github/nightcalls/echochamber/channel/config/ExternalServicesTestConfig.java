package io.github.nightcalls.echochamber.channel.config;

import io.github.nightcalls.echochamber.channel.user_api_service.UserApiServiceMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import io.github.nightcalls.echochamber.user.api.grpc.UserApiService;

@TestConfiguration
public class ExternalServicesTestConfig {
    @Bean
    public UserApiService getUserApiService() {
        return new UserApiServiceMock();
    }
}
