package io.axoniq.giftcard.query;

public class CountCardSummariesResult {

    private final int count;

    public CountCardSummariesResult(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
