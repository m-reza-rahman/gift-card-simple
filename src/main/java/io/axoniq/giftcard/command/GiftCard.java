package io.axoniq.giftcard.command;

import java.lang.invoke.MethodHandles;
import javax.persistence.Id;
import org.axonframework.commandhandling.CommandHandler;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class GiftCard {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    private String id;
    private int remainingValue;

    public GiftCard() {
        logger.info("Empty gift card created.");
    }

    @CommandHandler
    public GiftCard(IssueCommand issueCommand) {
        logger.info("Handling {}", issueCommand);

        if (issueCommand.getAmount() <= 0) {
            throw new IllegalArgumentException("Issue amount cannot be negative");
        }

        apply(new IssuedEvent(issueCommand.getId(), issueCommand.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCommand redeemCommand) {
        logger.info("Handling {}", redeemCommand);

        if (redeemCommand.getAmount() <= 0) {
            throw new IllegalArgumentException("Redeem amount cannot be negative");
        }

        if (redeemCommand.getAmount() > remainingValue) {
            throw new IllegalStateException("Redeem amount cannot be greater than remaining value");
        }

        apply(new RedeemedEvent(id, redeemCommand.getAmount()));
    }

    @EventSourcingHandler
    public void on(IssuedEvent IssuedEvent) {
        logger.info("applying {}", IssuedEvent);
        id = IssuedEvent.getId();
        remainingValue = IssuedEvent.getAmount();
        logger.info("new remaining value: {}", remainingValue);
    }

    @EventSourcingHandler
    public void on(RedeemedEvent evt) {
        logger.info("applying {}", evt);
        remainingValue -= evt.getAmount();
        logger.info("new remaining value: {}", remainingValue);
    }

}
