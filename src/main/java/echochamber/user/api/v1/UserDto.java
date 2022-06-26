package echochamber.user.api.v1;

import echochamber.user.User;

import java.time.OffsetDateTime;

public class UserDto {
    private final long id;
    private final String name;
    private final OffsetDateTime createdTs;
    private final OffsetDateTime updatedTs;
    private final OffsetDateTime lastActiveTs;
    private final boolean deleted;

    public UserDto(
            long id,
            String name,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            OffsetDateTime lastActiveTs,
            boolean deleted
    ) {
        this.id = id;
        this.name = name;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.lastActiveTs = lastActiveTs;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    public OffsetDateTime getLastActiveTs() {
        return lastActiveTs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdTs=" + createdTs +
                ", updatedTs=" + updatedTs +
                ", lastActiveTs=" + lastActiveTs +
                ", deleted=" + deleted +
                '}';
    }

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getName().getName(),
                user.getCreatedTs(),
                user.getUpdatedTs(),
                user.getLastLoginInfo().getTimestamp(),
                user.isDeleted()
        );
    }
}
