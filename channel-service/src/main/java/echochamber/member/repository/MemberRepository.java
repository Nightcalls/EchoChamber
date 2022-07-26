package echochamber.member.repository;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private static final Logger log = LoggerFactory.getLogger(MemberRepository.class);

    private final DSLContext jooqContext;

    public MemberRepository(DSLContext jooqContext) {
        this.jooqContext = jooqContext;
    }
}
