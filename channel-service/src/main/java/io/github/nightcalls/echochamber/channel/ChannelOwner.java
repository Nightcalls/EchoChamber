package io.github.nightcalls.echochamber.channel;

public class ChannelOwner {
    private final long userId;

    public ChannelOwner(long ownerId) {
        this.userId = ownerId;
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