package io.axoniq.giftcard.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class GiftCard {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String id;
    private int remainingValue;

    public GiftCard() {
        logger.info("empty constructor invoked");
    }

    @CommandHandler
    public GiftCard(IssueCommand cmd) {
        logger.info("handling {}", cmd);
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        apply(new IssuedEvent(cmd.getId(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCommand cmd) {
        logger.info("handling {}", cmd);
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        if (cmd.getAmount() > remainingValue) {
            throw new IllegalStateException("amount > remaining value");
        }
        apply(new RedeemedEvent(id, cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(IssuedEvent evt) {
        logger.info("applying {}", evt);
        id = evt.getId();
        remainingValue = evt.getAmount();
        logger.info("new remaining value: {}", remainingValue);
    }

    @EventSourcingHandler
    public void on(RedeemedEvent evt) {
        logger.info("applying {}", evt);
        remainingValue -= evt.getAmount();
        logger.info("new remaining value: {}", remainingValue);
    }

}
