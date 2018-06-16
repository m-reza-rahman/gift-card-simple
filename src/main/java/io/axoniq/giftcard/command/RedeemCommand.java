package io.axoniq.giftcard.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RedeemCommand {

    @TargetAggregateIdentifier
    private String id;
    private int amount;

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
