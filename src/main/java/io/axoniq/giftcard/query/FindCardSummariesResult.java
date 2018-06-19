package io.axoniq.giftcard.query;

import java.util.List;

public class FindCardSummariesResult {

    private final List<CardSummary> data;

    public FindCardSummariesResult(List<CardSummary> data) {
        this.data = data;
    }

    public List<CardSummary> getData() {
        return data;
    }
}
