package io.axoniq.giftcard.query;

public class CardSummariesUpdatedEvent {

    private final String id;

    public CardSummariesUpdatedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Card summaries updated event with ID=" + id;
    }
}