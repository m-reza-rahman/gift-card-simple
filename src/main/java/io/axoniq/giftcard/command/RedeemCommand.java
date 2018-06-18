package io.axoniq.giftcard.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RedeemCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final int amount;

    public RedeemCommand(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
