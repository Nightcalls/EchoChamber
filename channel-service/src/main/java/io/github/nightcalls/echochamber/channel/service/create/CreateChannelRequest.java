package io.github.nightcalls.echochamber.channel.service.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class CreateChannelRequest {
    @Nullable
    private final String name;
    @Nullable
    private final long userId;
    @Nullable
    private final OffsetDateTime createdTs;
    @Nullable
    private final OffsetDateTime updatedTs;
    @Nullable
    private final boolean deleted;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreateChannelRequest(
            @JsonProperty("name") @Nullable String name,
            @JsonProperty("userId") @Nullable long userId,
            @JsonProperty("createdTs") @Nullable OffsetDateTime createdTs,
            @JsonProperty("updatedTs") @Nullable OffsetDateTime updatedTs,
            @JsonProperty("deleted") @Nullable boolean deleted
    ) {
        this.name = name;
        this.userId = userId;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.deleted = deleted;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public long getUserId() {
        return userId;
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
    public boolean isDeleted() {
        return deleted;
    }
}
