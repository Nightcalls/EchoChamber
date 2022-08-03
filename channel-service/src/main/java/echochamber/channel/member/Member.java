package echochamber.channel.member;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Member {
    private final long id;
    private final long channelId;
    private final MemberUser user;
    private MemberName name;
    private MemberRole role;
    private OffsetDateTime inactiveSince;
    private final OffsetDateTime createdTs;
    private OffsetDateTime updatedTs;
    private OffsetDateTime lastActivityTs;
    private boolean deleted;

    public Member(
            long id,
            long channelId,
            MemberUser user,
            MemberName name,
            MemberRole role,
            OffsetDateTime inactiveSince,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            OffsetDateTime lastActivityTs,
            boolean deleted
    ) {
        this.id = id;
        this.channelId = channelId;
        this.user = user;
        setName(name);
        this.role = role;
        this.inactiveSince = inactiveSince;
        if (createdTs == null) {
            throw new IllegalArgumentException("createdTs can't be null");
        }
        this.createdTs = createdTs;
        setUpdatedTs(updatedTs);
        this.lastActivityTs = lastActivityTs;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public long getChannelId() {
        return channelId;
    }

    public MemberUser getUser() {
        return user;
    }

    public MemberName getName() {
        return name;
    }

    public void setName(MemberName name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        this.name = name;
    }

    public MemberRole getRole() {
        return role;
    }

    public OffsetDateTime getInactiveSince() {
        return inactiveSince;
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

    public OffsetDateTime getLastActivityTs() {
        return lastActivityTs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id && channelId == member.channelId && deleted == member.deleted && user.equals(member.user) && name.equals(member.name) && role.equals(member.role) && inactiveSince.equals(member.inactiveSince) && createdTs.equals(member.createdTs) && updatedTs.equals(member.updatedTs) && lastActivityTs.equals(member.lastActivityTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, user, name, role, inactiveSince, createdTs, updatedTs, lastActivityTs, deleted);
    }

    public static class Builder {
        private Long id;
    }
}
