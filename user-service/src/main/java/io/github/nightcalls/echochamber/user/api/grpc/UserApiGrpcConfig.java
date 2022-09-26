package io.github.nightcalls.echochamber.user.api.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class UserApiGrpcConfig {
    private final int grpcServerPort;

    public UserApiGrpcConfig(@Value("${user.api.grpc-server.port}") int grpcServerPort) {
        this.grpcServerPort = grpcServerPort;
    }

    @Bean
    public Server userApiGrpcServer(UserApiGrpcImpl userApiGrpcImpl) throws IOException {
        return ServerBuilder.forPort(grpcServerPort)
                .addService(userApiGrpcImpl)
                .build()
                .start();
    }
}