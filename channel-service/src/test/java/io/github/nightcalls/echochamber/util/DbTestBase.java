package io.github.nightcalls.echochamber.util;

import io.github.nightcalls.echochamber.config.Profiles;
import io.zonky.test.db.postgres.embedded.LiquibasePreparer;
import io.zonky.test.db.postgres.junit5.EmbeddedPostgresExtension;
import io.zonky.test.db.postgres.junit5.PreparedDbExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles(Profiles.TEST)
public abstract class DbTestBase {
    @RegisterExtension
    static final PreparedDbExtension EMBEDDED_POSTGRES = EmbeddedPostgresExtension.preparedDatabase(
            LiquibasePreparer.forClasspathLocation("classpath:sql/changelogs.xml")
    );
}
