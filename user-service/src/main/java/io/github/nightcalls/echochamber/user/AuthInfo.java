package io.github.nightcalls.echochamber.user;

import java.time.OffsetDateTime;
import java.util.Objects;

public class AuthInfo {
    private final String id;
    private final String token;
    private final String refreshToken;
    private final OffsetDateTime refreshedTs;

    public AuthInfo(String id, String token, String refreshToken, OffsetDateTime refreshedTs) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id is empty or null");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token is empty or null");
        }
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("RefreshToken is empty or null");
        }
        if (refreshedTs == null) {
            throw new IllegalArgumentException("RefreshedTs is null");
        }
        this.id = id;
        this.token = token;
        this.refreshToken = refreshToken;
        this.refreshedTs = refreshedTs;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OffsetDateTime getRefreshedTs() {
        return refreshedTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfo authInfo = (AuthInfo) o;
        return id.equals(authInfo.id)
                && token.equals(authInfo.token)
                && refreshToken.equals(authInfo.refreshToken)
                && refreshedTs.equals(authInfo.refreshedTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, refreshToken, refreshedTs);
    }
}
