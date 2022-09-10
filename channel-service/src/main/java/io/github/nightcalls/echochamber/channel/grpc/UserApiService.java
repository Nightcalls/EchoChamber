package io.github.nightcalls.echochamber.channel.grpc;

import io.github.nightcalls.echochamber.user.api.grpc.UserApi;

import java.util.Collection;

public interface UserApiService {
    UserApi.User getUser(long id);

    Collection<UserApi.User> getUsers(Collection<Long> ids);
}
