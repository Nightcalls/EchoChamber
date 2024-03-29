package io.github.nightcalls.echochamber.user.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.nightcalls.echochamber.user.AuthInfo;

import javax.annotation.Nullable;
import java.time.OffsetDateTime;

// FIXME dev-only, remove later
public class CreateUserRequest {
    @Nullable
    private final String name;
    @Nullable
    private final OffsetDateTime createdTs;
    @Nullable
    private final OffsetDateTime updatedTs;
    @Nullable
    private final AuthInfo discordAuthInfo;
    @Nullable
    private final String ip;
    @Nullable
    private final boolean deleted;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreateUserRequest(
            @JsonProperty("name") @Nullable String name,
            @JsonProperty("createdTs") @Nullable OffsetDateTime createdTs,
            @JsonProperty("updatedTs") @Nullable OffsetDateTime updatedTs,
            @JsonProperty("discordAuthInfo") @Nullable AuthInfo discordAuthInfo,
            @JsonProperty("ip") @Nullable String ip,
            @JsonProperty("deleted") @Nullable boolean deleted
    ) {
        this.name = name;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.discordAuthInfo = discordAuthInfo;
        this.ip = ip;
        this.deleted = deleted;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Nullable
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Nullable
    public AuthInfo getDiscordAuthInfo() {
        return discordAuthInfo;
    }

    @Nullable
    public String getIp() {
        return ip;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
