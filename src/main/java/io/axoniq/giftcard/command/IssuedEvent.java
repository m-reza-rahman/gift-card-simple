package io.axoniq.giftcard.command;

public class IssuedEvent {

    private final String id;
    private final int amount;

    public IssuedEvent(String id, int amount) {
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
