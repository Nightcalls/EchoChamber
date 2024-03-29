package io.github.nightcalls.echochamber.channel.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateChannelRequest {
    private final String name;
    private final long ownerId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreateChannelRequest(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "ownerId", required = true) long ownerId
    ) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public long getOwnerId() {
        return ownerId;
    }
}
