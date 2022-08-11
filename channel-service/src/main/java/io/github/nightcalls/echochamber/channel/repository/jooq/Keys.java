/*
 * This file is generated by jOOQ.
 */
package io.github.nightcalls.echochamber.channel.repository.jooq;


import io.github.nightcalls.echochamber.channel.repository.jooq.tables.Channel;
import io.github.nightcalls.echochamber.channel.repository.jooq.tables.records.ChannelRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * c.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChannelRecord> CHANNEL_PKEY = Internal.createUniqueKey(Channel.CHANNEL, DSL.name("channel_pkey"), new TableField[] { Channel.CHANNEL.ID }, true);
}