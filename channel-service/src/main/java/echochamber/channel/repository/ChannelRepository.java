package echochamber.channel.repository;

import echochamber.channel.Channel;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ChannelRepository {
    private static final Logger log = LoggerFactory.getLogger(ChannelRepository.class);

    private final DSLContext jooqContext;

    public ChannelRepository(DSLContext jooqContext) {
        this.jooqContext = jooqContext;
    }

    //TODO Launch jOOQ
    public Optional<Channel> findChannelById(long id) {
        return jooqContext.selectFrom(CHANNEL).where(CHANNEL.ID.eq(id).fetchOptional().map(this::asEntity));
    }

    public Optional<Channel> findChannelByIdForUpdate(long id) {
        return jooqContext.selectFrom(CHANNEL).where(CHANNEL.ID.eq(id)).forUpdate().fetchOptional()
                .map(this::asEntity);
    }

    public List<Channel> findChannelsByIds(Collection<Long> ids) {
        return jooqContext.selectFrom(CHANNEL).where(CHANNEL.ID.in(ids)).fetchStream().map(this::asEntity)
                .collect(Collectors.toList());
    }

    public void insertChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel can't be null");
        }
        jooqContext.insertInto(CHANNEL).set(asRecord(channel)).execute();
        log.debug("Inserted channel {}", channel.getId());
    }

    public void insertChannels(Collection<Channel> channels) {
        jooqContext.batchInsert(channels.stream().map(this::asRecord).collect(Collectors.toList())).execute();
        if (log.isDebugEnabled()) {
            var ids = channels.stream().map(Channel::getId).collect(Collectors.toList());
            log.debug("Updated {} channels: {}", channels.size(), ids);
        }
    }

    public void updateChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel can't be null");
        }
        jooqContext.executeUpdate(asRecord(channel));
        log.debug("Updated channel {}", channel.getId());
    }

    public void updateChannels(Collection<Channel> channels) {
        jooqContext.batchUpdate(channels.stream().map(this::asRecord).toArray(ChannelRecord[]::new)).execute();
        if (log.isDebugEnabled()) {
            var ids = channels.stream().map(Channel::getId).collect(Collectors.toList());
            log.debug("Updated {} channels: {}", channels.size(), ids);
        }
    }

    public void truncate() {
        jooqContext.truncateTable(CHANNEL).execute();
    }

    public long generateNewChannelId() {
        return jooqContext.nextval(Sequences.CHANNEL_ID_SEQ);
    }

    public Channel asEntity(ChannelRecord record) {
        return new Channel(

        )
    }

    private ChannelRecord asRecord(Channel channel) {
        var record = new ChannelRecord;

    }
}
