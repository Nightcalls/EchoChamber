/*
 * This file is generated by jOOQ.
 */
package io.github.nightcalls.echochamber.user.repository.jooq;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in u.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>u.user_id_seq</code>
     */
    public static final Sequence<Long> USER_ID_SEQ = Internal.createSequence("user_id_seq", U.U, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
