package io.axoniq.giftcard.query;

public class FindCardSummariesQuery {

    private final int offset;
    private final int limit;

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

    @Override
    public String toString() {
        return "Find card summaries query with offset=" + offset + ", limit=" + limit;
    }
}
