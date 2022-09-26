package io.github.nightcalls.echochamber.user.api.grpc;

import java.util.Collection;
import java.util.Optional;

public class UserApiServiceWrapper implements UserApiService {
    private final UserApiServiceGrpc.UserApiServiceBlockingStub stub;

    public UserApiServiceWrapper(UserApiServiceGrpc.UserApiServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public Optional<UserApi.User> getUser(long id) {
        UserApi.GetUserRequest getUserRequest = UserApi.GetUserRequest.newBuilder().setUserId(id).build();
        var user = stub.getUser(getUserRequest);
        if (user.hasUser()) {
            return Optional.of(user.getUser());
        }
        return Optional.empty();
    }

    @Override
    public Collection<UserApi.User> getUsers(Collection<Long> ids) {
        UserApi.GetUsersRequest getUsersRequest = UserApi.GetUsersRequest.newBuilder().addAllUserId(ids).build();
        return stub.getUsers(getUsersRequest).getUserList();
    }
}
