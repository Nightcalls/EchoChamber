package echochamber.user;

import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.Objects;

public class User {
    private final long id;
    private UserName name;
    private final OffsetDateTime createdTs;
    private OffsetDateTime updatedTs;
    @Nullable
    private AuthInfo discordAuthInfo;
    private LastLoginInfo lastLoginInfo;
    private boolean deleted;

    public User(
            long id,
            UserName name,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            AuthInfo discordAuthInfo,
            LastLoginInfo lastLoginInfo,
            boolean deleted
    ) {
        this.id = id;
        setName(name);
        if (createdTs == null) {
            throw new IllegalArgumentException("createdTs can't be null");
        }
        this.createdTs = createdTs;
        setUpdatedTs(updatedTs);
        this.discordAuthInfo = discordAuthInfo;
        setLastLoginInfo(lastLoginInfo);
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        this.name = name;
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

    @Nullable
    public AuthInfo getDiscordAuthInfo() {
        return discordAuthInfo;
    }

    public void setDiscordAuthInfo(@Nullable AuthInfo discordAuthInfo) {
        this.discordAuthInfo = discordAuthInfo;
    }

    public LastLoginInfo getLastLoginInfo() {
        return lastLoginInfo;
    }

    public void setLastLoginInfo(LastLoginInfo lastLoginInfo) {
        if (lastLoginInfo == null) {
            throw new IllegalArgumentException("lastLoginInfo can't be null");
        }
        this.lastLoginInfo = lastLoginInfo;
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
        User user = (User) o;
        return id == user.id && deleted == user.deleted && name.equals(user.name) && createdTs.equals(user.createdTs) && updatedTs.equals(user.updatedTs) && Objects.equals(discordAuthInfo, user.discordAuthInfo) && lastLoginInfo.equals(user.lastLoginInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdTs, updatedTs, discordAuthInfo, lastLoginInfo, deleted);
    }

    public static class Builder {
        private Long id;
        private UserName name;
        private OffsetDateTime createdTs;
        private OffsetDateTime updatedTs;
        @Nullable
        private AuthInfo discordAuthInfo;
        private LastLoginInfo lastLoginInfo;
        private boolean deleted;

        public Builder() {
        }

        public Builder(User user) {
            id(user.getId());
            name(user.getName());
            createdTs(user.getCreatedTs());
            updatedTs(user.getUpdatedTs());
            discordAuthInfo(user.getDiscordAuthInfo());
            lastLoginInfo(user.getLastLoginInfo());
            deleted(user.isDeleted());
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            return name(new UserName(name));
        }

        public Builder name(UserName name) {
            this.name = name;
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

        public Builder discordAuthInfo(AuthInfo discordAuthInfo) {
            this.discordAuthInfo = discordAuthInfo;
            return this;
        }

        public Builder lastLoginInfo(String ip) {
            return lastLoginInfo(OffsetDateTime.now(), ip);
        }

        public Builder lastLoginInfo(OffsetDateTime timestamp, String ip) {
            return lastLoginInfo(new LastLoginInfo(timestamp, ip));
        }

        public Builder lastLoginInfo(LastLoginInfo lastLoginInfo) {
            this.lastLoginInfo = lastLoginInfo;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public User build() {
            this.createdTs = Objects.requireNonNullElseGet(createdTs, OffsetDateTime::now);
            this.updatedTs = Objects.requireNonNullElseGet(updatedTs, OffsetDateTime::now);
            return new User(
                    id,
                    name,
                    createdTs,
                    updatedTs,
                    discordAuthInfo,
                    lastLoginInfo,
                    deleted
            );
        }
    }
}