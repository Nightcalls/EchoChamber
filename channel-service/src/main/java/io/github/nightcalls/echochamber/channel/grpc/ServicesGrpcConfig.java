package io.github.nightcalls.echochamber.channel.grpc;

import io.github.nightcalls.echochamber.user.api.grpc.UserApiServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesGrpcConfig {
    private final String grpcUserApiUrl;

    public ServicesGrpcConfig(@Value("${user.api.grpc-server.url}") String grpcUserApiUrl) {
        this.grpcUserApiUrl = grpcUserApiUrl;
    }

    @Bean
    public UserApiService getUserApiService() {
        ManagedChannel grpcUserApiToChannel = ManagedChannelBuilder.forTarget(String.valueOf(grpcUserApiUrl))
                .usePlaintext()
                .build();
        var stub = UserApiServiceGrpc.newBlockingStub(grpcUserApiToChannel);
        return new UserApiServiceWrapper(stub);
    }
}
