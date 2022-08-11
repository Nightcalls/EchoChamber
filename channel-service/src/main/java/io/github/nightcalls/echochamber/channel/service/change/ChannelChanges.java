package io.github.nightcalls.echochamber.channel.service.change;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

public class ChannelChanges {
    @Nullable
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ChannelChanges(
            @JsonProperty("name") @Nullable String name
    ) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @JsonIgnore
    public boolean isNameChanged() {
        return name != null;
    }

    @JsonIgnore
    public boolean hasChanges() {
        return isNameChanged();
    }
}
