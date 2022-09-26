package io.github.nightcalls.echochamber.channel.repository;

import io.github.nightcalls.echochamber.channel.Channel;
import io.github.nightcalls.echochamber.channel.ChannelName;
import io.github.nightcalls.echochamber.channel.ChannelOwner;
import io.github.nightcalls.echochamber.channel.repository.jooq.Sequences;
import io.github.nightcalls.echochamber.channel.repository.jooq.tables.records.ChannelRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.nightcalls.echochamber.channel.repository.jooq.Tables.CHANNEL;

@Repository
public class ChannelRepository {
    private static final Logger log = LoggerFactory.getLogger(ChannelRepository.class);

    private final DSLContext jooqContext;

    public ChannelRepository(DSLContext jooqContext) {
        this.jooqContext = jooqContext;
    }

    public Optional<Channel> findChannelById(long id) {
        try (var query = jooqContext.selectFrom(CHANNEL)) {
            return query.where(CHANNEL.ID.eq(id)).fetchOptional().map(this::asEntity);
        }
    }

    public Optional<Channel> findChannelByIdForUpdate(long id) {
        try (var query = jooqContext.selectFrom(CHANNEL)) {
            return query.where(CHANNEL.ID.eq(id)).forUpdate().fetchOptional().map(this::asEntity);
        }
    }

    public List<Channel> findChannelsByIds(Collection<Long> ids) {
        try (var query = jooqContext.selectFrom(CHANNEL)) {
            return query.where(CHANNEL.ID.in(ids)).fetchStream().map(this::asEntity).collect(Collectors.toList());
        }
    }

    public Optional<Channel> findChannelByName(String name) {
        try (var query = jooqContext.selectFrom(CHANNEL)) {
            return query.where(CHANNEL.NAME.eq(name)).fetchOptional().map(this::asEntity);
        }
    }

    public void insertChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel can't be null");
        }
        try (var query = jooqContext.insertInto(CHANNEL).set(asRecord(channel))) {
            query.execute();
            log.debug("Inserted channel {}", channel.getId());
        }
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
        try (var query = jooqContext.truncateTable(CHANNEL)) {
            query.execute();
        }
    }

    public long generateNewChannelId() {
        return jooqContext.nextval(Sequences.CHANNEL_ID_SEQ);
    }

    public Channel asEntity(ChannelRecord record) {
        return new Channel(
                record.getId(),
                new ChannelName(record.getName()),
                ChannelOwner.createChannelOwnerFromRawUserId(record.getOwnerId()),
                record.getCreatedTs(),
                record.getUpdatedTs(),
                record.getDeleted()
        );
    }

    private ChannelRecord asRecord(Channel channel) {
        var record = new ChannelRecord();
        record.setId(channel.getId());
        record.setName(channel.getName().getName());
        record.setOwnerId(channel.getOwner().getOwnerId());
        record.setCreatedTs(channel.getCreatedTs());
        record.setUpdatedTs(channel.getUpdatedTs());
        record.setDeleted(channel.isDeleted());
        return record;
    }
}
