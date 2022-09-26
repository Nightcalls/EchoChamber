package io.github.nightcalls.echochamber.user.api.grpc;

import java.util.Collection;
import java.util.Optional;

public interface UserApiService {
    Optional<UserApi.User> getUser(long id);

    Collection<UserApi.User> getUsers(Collection<Long> ids);
}
