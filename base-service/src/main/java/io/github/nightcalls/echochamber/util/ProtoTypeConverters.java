package io.github.nightcalls.echochamber.util;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class ProtoTypeConverters {
    private ProtoTypeConverters() {
    }

    public static Timestamp offsetDateTimeToTimestamp(OffsetDateTime offsetDateTime) {
        return Timestamp.newBuilder().setSeconds(offsetDateTime.toEpochSecond()).setNanos(offsetDateTime.getNano()).build();
    }

    public static OffsetDateTime timestampToOffsetDateTime(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneOffset.UTC);
    }

}
