package io.github.nightcalls.echochamber.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {
    @Bean
    public DSLContext jooqDslContext(DataSource dataSource) {
        var settings = new Settings();
        return DSL.using(dataSource, SQLDialect.POSTGRES, settings);
    }
}
