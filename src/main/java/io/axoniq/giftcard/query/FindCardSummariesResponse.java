package io.axoniq.giftcard.query;

import java.util.List;

public class FindCardSummariesResponse {

    private final List<CardSummary> data;

    public FindCardSummariesResponse(List<CardSummary> data) {
        this.data = data;
    }

    public List<CardSummary> getData() {
        return data;
    }
}
