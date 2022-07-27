package echochamber.user.repository;

import echochamber.user.LastLoginInfo;
import echochamber.user.User;
import echochamber.user.UserName;
import echochamber.user.repository.jooq.Sequences;
import echochamber.user.repository.jooq.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static echochamber.user.repository.jooq.tables.User.USER;

@Repository
public class UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final DSLContext jooqContext;

    public UserRepository(DSLContext jooqContext) {
        this.jooqContext = jooqContext;
    }

    public Optional<User> findUserById(long id) {
        return jooqContext.selectFrom(USER).where(USER.ID.eq(id)).fetchOptional().map(this::asEntity);
    }

    public Optional<User> findUserByIdForUpdate(long id) {
        return jooqContext.selectFrom(USER).where(USER.ID.eq(id)).forUpdate().fetchOptional().map(this::asEntity);
    }

    public List<User> findUsersByIds(Collection<Long> ids) {
        return jooqContext.selectFrom(USER).where(USER.ID.in(ids)).fetchStream().map(this::asEntity)
                .collect(Collectors.toList());
    }

    public void insertUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user can't be null");
        }
        jooqContext.insertInto(USER).set(asRecord(user)).execute();
        log.debug("Inserted user {}", user.getId());
    }

    public void insertUsers(Collection<User> users) {
        jooqContext.batchInsert(users.stream().map(this::asRecord).collect(Collectors.toList())).execute();
        if (log.isDebugEnabled()) {
            var ids = users.stream().map(User::getId).collect(Collectors.toList());
            log.debug("Updated {} users: {}", users.size(), ids);
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user can't be null");
        }
        jooqContext.executeUpdate(asRecord(user));
        log.debug("Updated user {}", user.getId());
    }

    public void updateUsers(Collection<User> users) {
        jooqContext.batchUpdate(users.stream().map(this::asRecord).toArray(UserRecord[]::new)).execute();
        if (log.isDebugEnabled()) {
            var ids = users.stream().map(User::getId).collect(Collectors.toList());
            log.debug("Updated {} users: {}", users.size(), ids);
        }
    }

    public void truncate() {
        jooqContext.truncateTable(USER).execute();
    }

    public long generateNewUserId() {
        return jooqContext.nextval(Sequences.USER_ID_SEQ);
    }

    public User asEntity(UserRecord record) {
        return new User(
                record.getId(),
                new UserName(record.getName()),
                record.getCreatedTs(),
                record.getUpdatedTs(),
                null,
                new LastLoginInfo(record.getLastLoginTs(), record.getLastLoginIp()),
                record.getDeleted()
        );
    }

    private UserRecord asRecord(User user) {
        var record = new UserRecord();
        record.setId(user.getId());
        record.setName(user.getName().getName());
        record.setCreatedTs(user.getCreatedTs());
        record.setUpdatedTs(user.getUpdatedTs());
        record.setLastLoginTs(user.getLastLoginInfo().getTimestamp());
        record.setLastLoginIp(user.getLastLoginInfo().getIp());
        record.setDeleted(user.isDeleted());
        return record;
    }
}
