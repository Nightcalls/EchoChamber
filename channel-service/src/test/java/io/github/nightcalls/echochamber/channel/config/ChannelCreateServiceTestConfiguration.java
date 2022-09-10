package io.github.nightcalls.echochamber.channel.config;

import io.github.nightcalls.echochamber.channel.grpc.UserApiService;
import io.github.nightcalls.echochamber.channel.user_api_service.UserApiServiceMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ChannelCreateServiceTestConfiguration {
    @Bean
    public UserApiService getUserApiService() {
        return new UserApiServiceMock();
    }
}
