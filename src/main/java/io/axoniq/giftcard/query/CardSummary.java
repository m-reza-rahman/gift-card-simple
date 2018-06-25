package io.axoniq.giftcard.query;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "CardSummary.findCardSummaries",
            query = "SELECT c FROM CardSummary c ORDER BY c.id"),
    @NamedQuery(name = "CardSummary.countCardSummaries",
            query = "SELECT COUNT(c) FROM CardSummary c")})
public class CardSummary {

    @Id
    private final String id;
    private final int initialValue;
    private final Instant issuedAt;
    private int remainingValue;

    protected CardSummary() {
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

    @Override
    public String toString() {
        return "Card summary with ID=" + id + ", initialValue=" + initialValue + ", issuedAt=" + issuedAt + ", remainingValue=" + remainingValue;
    }
}
