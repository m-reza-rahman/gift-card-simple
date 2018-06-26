package io.axoniq.giftcard.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedeemedEvent {

    private final String id;
    private final int amount;

    @JsonCreator
    public RedeemedEvent(
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
        return "Redeemed event with ID=" + id + ", amount=" + amount;
    }
}
