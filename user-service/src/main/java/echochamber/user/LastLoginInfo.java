package echochamber.user;

import java.time.OffsetDateTime;
import java.util.Objects;

public class LastLoginInfo {
    private final OffsetDateTime timestamp;
    private final String ip;

    public LastLoginInfo(OffsetDateTime timestamp, String ip) {
        if (timestamp == null) {
            throw new IllegalArgumentException("timestamp can not be null");
        }
        if (ip == null) {
            throw new IllegalArgumentException("ip can not be null");
        }
        if (ip.isBlank()) {
            throw new IllegalArgumentException("ip can not be empty");
        }
        this.timestamp = timestamp;
        this.ip = ip;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastLoginInfo that = (LastLoginInfo) o;
        return timestamp.equals(that.timestamp) && ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, ip);
    }
}
