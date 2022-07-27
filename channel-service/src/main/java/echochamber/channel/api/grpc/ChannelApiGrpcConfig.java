package echochamber.channel.api.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ChannelApiGrpcConfig {
    private final int grpcServerPort;

    public ChannelApiGrpcConfig(@Value("50002") int grpcServerPort) {
        this.grpcServerPort = grpcServerPort;
    }

    @Bean
    public Server channelApiGrpcServer(ChannelApiGrpcImpl channelApiGrpcImpl) throws IOException {
        return ServerBuilder.forPort(grpcServerPort)
                .addService(channelApiGrpcImpl)
                .build()
                .start();
    }
}
