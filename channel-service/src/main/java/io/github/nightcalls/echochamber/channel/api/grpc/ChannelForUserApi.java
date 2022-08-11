package io.github.nightcalls.echochamber.channel.api.grpc;

import io.github.nightcalls.echochamber.user.api.grpc.UserApi;
import io.github.nightcalls.echochamber.user.api.grpc.UserApiServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//grpc client for UserApi
public class ChannelForUserApi {

    //id come from front
    public static UserApi.GetUserResponse grpcUserResponse(long userId) {
        ManagedChannel grpcChannel = ManagedChannelBuilder.forTarget("localhost:50001")
                .usePlaintext()
                .build();

        UserApiServiceGrpc.UserApiServiceBlockingStub stub =
                UserApiServiceGrpc.newBlockingStub(grpcChannel);

        var response = stub
                .getUser(UserApi.GetUserRequest.newBuilder().setUserId(userId).build());

        return response;
    }
}
