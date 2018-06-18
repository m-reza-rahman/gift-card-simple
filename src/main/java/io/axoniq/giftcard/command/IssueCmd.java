package io.axoniq.giftcard.command;

public class IssueCmd {

    private final String id;
    private final int amount;

    public IssueCmd(String id, int amount) {
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
