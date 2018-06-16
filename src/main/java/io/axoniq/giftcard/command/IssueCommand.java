package io.axoniq.giftcard.command;

public class IssueCommand {

    private String id;
    private int amount;

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
