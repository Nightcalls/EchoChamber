package echochamber.channel;

import echochamber.user.api.grpc.UserApi;

public class ChannelOwner {
    private final long userId;

    public ChannelOwner(UserApi.GetUserResponse userResponse) {
        this.userId = userResponse.getUser().getId();
    }

    public long getUserId() {
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
