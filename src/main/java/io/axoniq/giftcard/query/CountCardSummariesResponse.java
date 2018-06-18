package io.axoniq.giftcard.query;

public class CountCardSummariesResponse {

    private final int count;

    public CountCardSummariesResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
