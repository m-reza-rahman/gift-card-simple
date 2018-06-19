package io.axoniq.giftcard.query;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardSummary {

    @Id
    private final String id;
    private final int initialValue;
    private final Instant issuedAt;
    private int remainingValue;

    public CardSummary() {
        this.id = null;
        this.initialValue = 0;
        this.issuedAt = null;
        this.remainingValue = 0;
    }

    public CardSummary(String id, int initialValue, Instant issuedAt, int remainingValue) {
        this.id = id;
        this.initialValue = initialValue;
        this.issuedAt = issuedAt;
        this.remainingValue = remainingValue;
    }

    public String getId() {
        return id;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public int getRemainingValue() {
        return remainingValue;
    }

    void setRemainingValue(int remainingValue) {
        this.remainingValue = remainingValue;
    }
}
