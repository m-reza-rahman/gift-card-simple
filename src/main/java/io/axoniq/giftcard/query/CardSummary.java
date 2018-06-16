package io.axoniq.giftcard.query;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardSummary implements Serializable {

    @Id
    private final String id;
    private final Integer initialValue;
    private final Instant issuedAt;
    private Integer remainingValue;

    public CardSummary() {
        this.id = null;
        this.initialValue = null;
        this.issuedAt = null;
        this.remainingValue = null;
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
