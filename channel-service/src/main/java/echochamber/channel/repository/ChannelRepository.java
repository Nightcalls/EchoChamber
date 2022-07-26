package echochamber.channel.repository;

import echochamber.channel.Channel;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChannelRepository {
    private static final Logger log = LoggerFactory.getLogger(ChannelRepository.class);

    private final DSLContext jooqContext;

    public ChannelRepository(DSLContext jooqContext) {
        this.jooqContext = jooqContext;
    }

//    public Optional<Channel> findChannelById(long id) {
//        return jooqContext.selectFrom(CHANNEL).where(CHANNEL.ID.eq(id).fetchOptional.map(this::asEntity));
//    }

    /*
    findChannelByIdForUpdate?
    findChannelsByIds?

     */

}
