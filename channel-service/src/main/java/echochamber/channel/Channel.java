package echochamber.channel;

import echochamber.channel.api.UserForChannelDto;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Channel {
    private final long id;
    private ChannelName name;
    private ChannelOwner owner;
    private final OffsetDateTime createdTs;
    private OffsetDateTime updatedTs;
    private boolean deleted;

    public Channel(
            long id,
            ChannelName name,
            ChannelOwner owner,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            boolean deleted) {
        this.id = id;
        setName(name);
        setOwner(owner);
        if (createdTs == null) {
            throw new IllegalArgumentException("createdTs can't be null");
        }
        this.createdTs = createdTs;
        setUpdatedTs(updatedTs);
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public ChannelName getName() {
        return name;
    }

    public void setName(ChannelName name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        this.name = name;
    }

    public ChannelOwner getOwner() {
        return owner;
    }

    public void setOwner(ChannelOwner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner can't be null");
        }
        this.owner = owner;
    }

    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(OffsetDateTime updatedTs) {
        if (updatedTs == null) {
            throw new IllegalArgumentException("updatedTs can't be null");
        }
        this.updatedTs = updatedTs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return id == channel.id && deleted == channel.deleted && name.equals(channel.name) && owner.equals(channel.owner) && createdTs.equals(channel.createdTs) && updatedTs.equals(channel.updatedTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, createdTs, updatedTs, deleted);
    }

    public static class Builder {
        private Long id;
        private ChannelName name;
        private ChannelOwner owner;
        private OffsetDateTime createdTs;
        private OffsetDateTime updatedTs;
        private boolean deleted;

        public Builder() {
        }

        public Builder(Channel channel) {
            id(channel.getId());
            name(channel.getName());
            owner(channel.getOwner());
            createdTs(channel.getCreatedTs());
            updatedTs(channel.getUpdatedTs());
            deleted(channel.isDeleted());
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            return name(new ChannelName(name));
        }

        public Builder name(ChannelName name) {
            this.name = name;
            return this;
        }

        public Builder owner(UserForChannelDto userForChannelDto) {
            return owner(new ChannelOwner(userForChannelDto));
        }

        public Builder owner(ChannelOwner owner) {
            this.owner = owner;
            return this;
        }

        public Builder createdTs(OffsetDateTime createdTs) {
            this.createdTs = createdTs;
            return this;
        }

        public Builder updatedTs(OffsetDateTime updatedTs) {
            this.updatedTs = updatedTs;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Channel build() {
            this.createdTs = Objects.requireNonNullElseGet(createdTs, OffsetDateTime::now);
            this.updatedTs = Objects.requireNonNullElseGet(updatedTs, OffsetDateTime::now);
            return new Channel(
                    id,
                    name,
                    owner,
                    createdTs,
                    updatedTs,
                    deleted
            );
        }
    }
}
