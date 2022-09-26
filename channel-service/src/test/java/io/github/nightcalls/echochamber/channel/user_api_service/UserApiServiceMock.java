package io.github.nightcalls.echochamber.channel.user_api_service;

import io.github.nightcalls.echochamber.user.api.grpc.UserApiService;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi.User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserApiServiceMock implements UserApiService {
    Map<Long, User> users = new ConcurrentHashMap<>();

    public void addUser(User user) {
        long id = user.getId();
        if (users.putIfAbsent(id, user) != null) {
            throw new RuntimeException("Detected user id duplication in UserApiServiceMock: " + id);
        }
    }

    public void removeUser(User user) {
        users.remove(user.getId());
    }

    public void removeUser(long id) {
        users.remove(id);
    }

    @Override
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Collection<User> getUsers(Collection<Long> ids) {
        return ids.stream().map(users::get).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
