package io.axoniq.giftcard.query;

import io.axoniq.giftcard.command.IssuedEvent;
import io.axoniq.giftcard.command.RedeemedEvent;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CardSummaryProjection {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManager entityManager;
    private final EventBus queryUpdateEventBus;

    public CardSummaryProjection(EntityManager entityManager, @Qualifier("queryUpdates") EventBus queryUpdateEventBus) {
        this.entityManager = entityManager;
        this.queryUpdateEventBus = queryUpdateEventBus;
    }

    @EventHandler
    public void on(IssuedEvent issuedEvent, @Timestamp Instant eventTimestamp) {
        logger.info("Projecting {}", issuedEvent);

        entityManager.persist(new CardSummary(issuedEvent.getId(), issuedEvent.getAmount(), eventTimestamp, issuedEvent.getAmount()));

        queryUpdateEventBus.publish(asEventMessage(new CardSummariesUpdatedEvent(issuedEvent.getId())));
    }

    @EventHandler
    public void on(RedeemedEvent redeemedEvent) {
        logger.info("Projecting {}", redeemedEvent);

        CardSummary summary = entityManager.find(CardSummary.class, redeemedEvent.getId());
        summary.setRemainingValue(summary.getRemainingValue() - redeemedEvent.getAmount());

        queryUpdateEventBus.publish(asEventMessage(new CardSummariesUpdatedEvent(redeemedEvent.getId())));
    }

    @QueryHandler
    public FindCardSummariesResult handle(FindCardSummariesQuery findCardSummariesQuery) {
        logger.info("Handling {}", findCardSummariesQuery);

        Query jpaQuery = entityManager.createNamedQuery("CardSummary.findCardSummaries",
                CardSummary.class);
        jpaQuery.setFirstResult(findCardSummariesQuery.getOffset());
        jpaQuery.setMaxResults(findCardSummariesQuery.getLimit());
        FindCardSummariesResult result = new FindCardSummariesResult(jpaQuery.getResultList());

        logger.info("Returning {}", result);

        return result;
    }

    @QueryHandler
    public CountCardSummariesResult handle(CountCardSummariesQuery countCardSummariesQuery) {
        logger.info("Handling {}", countCardSummariesQuery);

        Query jpaQuery = entityManager.createNamedQuery("CardSummary.countCardSummaries",
                Long.class);
        CountCardSummariesResult result = new CountCardSummariesResult(
                ((Long) jpaQuery.getSingleResult()).intValue());

        logger.info("Returning {}", result);

        return result;
    }
}
