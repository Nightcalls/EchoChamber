package echochamber.channel.api.v1;

import echochamber.channel.Channel;
import echochamber.channel.ChannelName;
import echochamber.channel.ChannelOwner;

import java.time.OffsetDateTime;

public class ChannelDto {
    private final long id;
    private final ChannelName name;
    private final ChannelOwner owner;
    private final OffsetDateTime createdTs;
    private final OffsetDateTime updatedTs;
    private final boolean deleted;

    public ChannelDto(
            long id,
            ChannelName name,
            ChannelOwner owner,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            boolean deleted
    ) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public ChannelName getName() {
        return name;
    }

    public ChannelOwner getOwner() {
        return owner;
    }

    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "ChannelDto{" +
                "id=" + id +
                ", name=" + name +
                ", owner=" + owner +
                ", createdTs=" + createdTs +
                ", updatedTs=" + updatedTs +
                ", deleted=" + deleted +
                '}';
    }

    public static ChannelDto fromChannel(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getName(),
                channel.getOwner(),
                channel.getCreatedTs(),
                channel.getUpdatedTs(),
                channel.isDeleted()
        );
    }
}
