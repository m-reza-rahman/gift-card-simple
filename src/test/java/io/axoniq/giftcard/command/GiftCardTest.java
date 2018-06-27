package io.axoniq.giftcard.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class GiftCardTest {

    private FixtureConfiguration<GiftCard> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void testIssueCard() {
        fixture.givenNoPriorActivity()
                .when(new IssueCommand("ABC123", 100))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new IssuedEvent("ABC123", 100));
    }

    @Test
    public void testIssueCardWithNegativeAmount() {
        fixture.givenNoPriorActivity()
                .when(new IssueCommand("ABC123", -1))
                .expectException(IllegalArgumentException.class)
                .expectNoEvents();
    }

    @Test
    public void testRedeemCard() {
        fixture.given(new IssuedEvent("ABC123", 100))
                .when(new RedeemCommand("ABC123", 15))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RedeemedEvent("ABC123", 15));
    }

    @Test
    public void testRedeemNegativeAmount() {
        fixture.given(new IssuedEvent("ABC123", 100))
                .when(new RedeemCommand("ABC123", -1))
                .expectException(IllegalArgumentException.class)
                .expectNoEvents();
    }

    @Test
    public void testRedeemAmountTooHigh() {
        fixture.given(new IssuedEvent("ABC123", 100))
                .andGiven(new RedeemedEvent("ABC123", 55))
                .when(new RedeemCommand("ABC123", 50))
                .expectException(IllegalStateException.class)
                .expectNoEvents();
    }
}
