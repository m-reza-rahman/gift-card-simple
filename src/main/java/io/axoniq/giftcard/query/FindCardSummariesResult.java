package io.axoniq.giftcard.query;

import java.util.List;

public class FindCardSummariesResult {

    private final List<CardSummary> cardSummaries;

    public FindCardSummariesResult(List<CardSummary> cardSummaries) {
        this.cardSummaries = cardSummaries;
    }

    public List<CardSummary> getCardSummaries() {
        return cardSummaries;
    }

    @Override
    public String toString() {
        return "Find card summaries result with cardSummaries=" + cardSummaries;
    }
}
