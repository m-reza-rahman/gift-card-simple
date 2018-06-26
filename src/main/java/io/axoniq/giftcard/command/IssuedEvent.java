package io.axoniq.giftcard.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssuedEvent {

    private final String id;
    private final int amount;

    @JsonCreator
    public IssuedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("amount") int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Issued event with ID=" + id + ", amount=" + amount;
    }
}
