package io.axoniq.giftcard.query;

public class FindCardSummariesQuery {

    private int offset;
    private int limit;

    public FindCardSummariesQuery(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
