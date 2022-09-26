package io.github.nightcalls.echochamber.channel;

import io.github.nightcalls.echochamber.user.api.grpc.UserApi;

public class ChannelOwner {
    private final long userId;

    public ChannelOwner(UserApi.User user) {
        this.userId = user.getId();
    }

    private ChannelOwner(long ownerId) {
        if (ownerId <= 0) {
            throw new IllegalArgumentException("Id (" + ownerId + ") must be a positive number");
        }
        this.userId = ownerId;
    }

    public static ChannelOwner createChannelOwnerFromRawUserId(long ownerId) {
        return new ChannelOwner(ownerId);
    }

    public long getOwnerId() {
        return userId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelOwner channelOwner = (ChannelOwner) o;
        return userId == channelOwner.userId;
    }

    public int hashCode() {
        return Long.hashCode(userId);
    }
}