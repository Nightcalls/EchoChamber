package echochamber.channel.api.grpc;

import echochamber.channel.ChannelOwner;
import echochamber.user.api.grpc.UserApi;
import echochamber.user.api.grpc.UserApiServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//grpc client for UserApi
public class ChannelForUserApi {

    //id come from front
    public ChannelOwner grpcUserResponse(long userId) {
        ManagedChannel grpcChannel = ManagedChannelBuilder.forTarget("localhost:50001")
                .usePlaintext()
                .build();

        UserApiServiceGrpc.UserApiServiceBlockingStub stub =
                UserApiServiceGrpc.newBlockingStub(grpcChannel);

        var response = stub
                .getUser(UserApi.GetUserRequest.newBuilder().setUserId(userId).build());

        return new ChannelOwner(response);
    }
}
