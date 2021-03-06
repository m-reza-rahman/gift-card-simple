package io.axoniq.giftcard.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class IssueCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final int amount;

    public IssueCommand(String id, int amount) {
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
        return "Issue command with ID=" + id + ", amount=" + amount;
    }
}
