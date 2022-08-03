package echochamber.channel.member.api.v1;

import echochamber.channel.member.Member;
import echochamber.channel.member.MemberName;
import echochamber.channel.member.MemberRole;
import echochamber.channel.member.MemberUser;

import java.time.OffsetDateTime;

public class MemberDto {
    private final long id;
    private final long channelId;
    private final MemberUser user;
    private final MemberName name;
    private final MemberRole role;
    private final OffsetDateTime inactiveSince;
    private final OffsetDateTime createdTs;
    private final OffsetDateTime updatedTs;
    private final OffsetDateTime lastActivityTs;


    public MemberDto(
            long id,
            long channelId,
            MemberUser user,
            MemberName name,
            MemberRole role,
            OffsetDateTime inactiveSince,
            OffsetDateTime createdTs,
            OffsetDateTime updatedTs,
            OffsetDateTime lastActivityTs
    ) {
        this.id = id;
        this.channelId = channelId;
        this.user = user;
        this.name = name;
        this.role = role;
        this.inactiveSince = inactiveSince;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.lastActivityTs = lastActivityTs;
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

    public OffsetDateTime getLastActivityTs() {
        return lastActivityTs;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", channelId=" + channelId +
                ", user=" + user +
                ", name=" + name +
                ", role=" + role +
                ", inactiveSince=" + inactiveSince +
                ", createdTs=" + createdTs +
                ", updatedTs=" + updatedTs +
                ", lastActivityTs=" + lastActivityTs +
                '}';
    }

    public static MemberDto fromMember(Member member) {
        return new MemberDto(
                member.getId(),
                member.getChannelId(),
                member.getUser(),
                member.getName(),
                member.getRole(),
                member.getInactiveSince(),
                member.getCreatedTs(),
                member.getUpdatedTs(),
                member.getLastActivityTs()
        );
    }
}
