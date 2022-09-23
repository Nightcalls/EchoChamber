package io.github.nightcalls.echochamber.channel.api.grpc;

import io.github.nightcalls.echochamber.config.Profiles;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class ChannelApiGrpcConfig {
    private final int grpcServerPort;

    public ChannelApiGrpcConfig(@Value("${channel.api.grpc-server.port}") int grpcServerPort) {
        this.grpcServerPort = grpcServerPort;
    }

    @Bean
    @Profile(Profiles.EXCEPT_FOR_TEST)
    public Server channelApiGrpcServer(ChannelApiGrpcImpl channelApiGrpcImpl) throws IOException {
        return ServerBuilder.forPort(grpcServerPort)
                .addService(channelApiGrpcImpl)
                .build()
                .start();
    }
}
