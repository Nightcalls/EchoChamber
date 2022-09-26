package io.github.nightcalls.echochamber.user.api.grpc;

import java.util.Collection;

public class UserApiServiceWrapper implements UserApiService {
    private final UserApiServiceGrpc.UserApiServiceBlockingStub stub;

    public UserApiServiceWrapper(UserApiServiceGrpc.UserApiServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public UserApi.User getUser(long id) {
        UserApi.GetUserRequest getUserRequest = UserApi.GetUserRequest.newBuilder().setUserId(id).build();
        return stub.getUser(getUserRequest).getUser();
    }

    @Override
    public Collection<UserApi.User> getUsers(Collection<Long> ids) {
        UserApi.GetUsersRequest getUsersRequest = UserApi.GetUsersRequest.newBuilder().addAllUserId(ids).build();
        return stub.getUsers(getUsersRequest).getUserList();
    }
}
