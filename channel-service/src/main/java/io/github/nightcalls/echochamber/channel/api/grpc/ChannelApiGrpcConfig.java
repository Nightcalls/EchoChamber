package io.github.nightcalls.echochamber.channel.api.grpc;

import io.github.nightcalls.echochamber.user.api.grpc.UserApiServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ChannelApiGrpcConfig {
    private final int grpcServerPort;
    private final int grpcUserServerPort;

    public ChannelApiGrpcConfig(@Value("${channel.api.grpc-server.port}") int grpcServerPort,
                                @Value("${user.api.grpc-server.port}") int grpcUserServerPort) throws IOException {
        this.grpcServerPort = grpcServerPort;
        this.grpcUserServerPort = grpcUserServerPort;
    }

    @Bean
    public Server channelApiGrpcServer(ChannelApiGrpcImpl channelApiGrpcImpl) throws IOException {
        return ServerBuilder.forPort(grpcServerPort)
                .addService(channelApiGrpcImpl)
                .build()
                .start();
    }

    @Bean
    public UserApiServiceGrpc.UserApiServiceBlockingStub userApiGrpcServerToChannel() {
        ManagedChannel grpcUserApiToChannel = ManagedChannelBuilder.forTarget(String.valueOf(grpcUserServerPort))
                .usePlaintext()
                .build();
        return UserApiServiceGrpc.newBlockingStub(grpcUserApiToChannel);
    }
}
