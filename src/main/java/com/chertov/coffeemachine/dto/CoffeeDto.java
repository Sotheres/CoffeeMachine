package com.chertov.coffeemachine.dto;

import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class CoffeeDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private CoffeeType type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant timeOfBrewing;

    public void setId(Long id) {
        this.id = id;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public Instant getTimeOfBrewing() {
        return timeOfBrewing;
    }

    public void setTimeOfBrewing(Instant timeOfBrewing) {
        this.timeOfBrewing = timeOfBrewing;
    }
}
