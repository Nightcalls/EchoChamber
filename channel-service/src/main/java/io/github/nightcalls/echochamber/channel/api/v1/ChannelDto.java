package io.github.nightcalls.echochamber.channel.api.v1;

import io.github.nightcalls.echochamber.channel.Channel;

import java.time.OffsetDateTime;

public class ChannelDto {
    private final long id;
    private final String name;
    private final long ownerId;
    private final OffsetDateTime createdTs;
    private final OffsetDateTime updatedTs;
    private final boolean deleted;

    public ChannelDto(
            long id,
            String name,
            long ownerId,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            boolean deleted
    ) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getOwnerId() {
        return ownerId;
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
                ", ownerId=" + ownerId +
                ", createdTs=" + createdTs +
                ", updatedTs=" + updatedTs +
                ", deleted=" + deleted +
                '}';
    }

    public static ChannelDto fromChannel(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getName().getName(),
                channel.getOwner().getOwnerId(),
                channel.getCreatedTs(),
                channel.getUpdatedTs(),
                channel.isDeleted()
        );
    }
}
