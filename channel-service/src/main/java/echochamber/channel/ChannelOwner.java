package echochamber.channel;

import echochamber.channel.api.UserForChannelDto;

public class ChannelOwner {
    private final long userId;

    public ChannelOwner(UserForChannelDto userForChannelDto) {
        this.userId = userForChannelDto.getId();
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
