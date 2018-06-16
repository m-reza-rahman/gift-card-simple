package io.axoniq.giftcard.query;

public class CardSummariesUpdatedEvt {

    private final String id;

    public CardSummariesUpdatedEvt(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
