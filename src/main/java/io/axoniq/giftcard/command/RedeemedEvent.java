package io.axoniq.giftcard.command;

public class RedeemedEvent {

    private String id;
    private int amount;

    public RedeemedEvent() {
        id = null;
        amount = 0;
    }

    public RedeemedEvent(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Redeemed event with ID=" + id + ", amount=" + amount;
    }
}
