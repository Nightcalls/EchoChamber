package echochamber.util;

import com.google.protobuf.Timestamp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static echochamber.util.ProtoTypeConverters.offsetDateTimeToTimestamp;
import static echochamber.util.ProtoTypeConverters.timestampToOffsetDateTime;

class ProtoTypeConvertersTest {

    @Test
    void testTimestampConversions() {
        OffsetDateTime before = OffsetDateTime.of(2022, 7, 25, 15, 39, 40, 125000000, ZoneOffset.of("+3"));
        Timestamp timestamp = offsetDateTimeToTimestamp(before);
        Assertions.assertThat(timestamp.getSeconds()).isEqualTo(1658752780);
        Assertions.assertThat(timestamp.getNanos()).isEqualTo(125000000);
        OffsetDateTime after = timestampToOffsetDateTime(timestamp);
        Assertions.assertThat(before).isAtSameInstantAs(after);
    }
}