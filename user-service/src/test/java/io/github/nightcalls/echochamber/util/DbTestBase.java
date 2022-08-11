package io.github.nightcalls.echochamber.util;

import io.zonky.test.db.postgres.embedded.LiquibasePreparer;
import io.zonky.test.db.postgres.junit5.EmbeddedPostgresExtension;
import io.zonky.test.db.postgres.junit5.PreparedDbExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class DbTestBase {
    @RegisterExtension
    static final PreparedDbExtension EMBEDDED_POSTGRES = EmbeddedPostgresExtension.preparedDatabase(
            LiquibasePreparer.forClasspathLocation("classpath:sql/changelogs.xml")
    );
}
