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
    public FindCardSummariesResult handle(FindCardSummariesQuery query) {
        logger.info("handling {}", query);
        Query jpaQuery = entityManager.createQuery("SELECT c FROM CardSummary c ORDER BY c.id",
                CardSummary.class);
        jpaQuery.setFirstResult(query.getOffset());
        jpaQuery.setMaxResults(query.getLimit());
        FindCardSummariesResult response = new FindCardSummariesResult(jpaQuery.getResultList());
        logger.info("returning {}", response);
        return response;
    }

    @QueryHandler
    public CountCardSummariesResult handle(CountCardSummariesQuery query) {
        logger.info("handling {}", query);
        Query jpaQuery = entityManager.createQuery("SELECT COUNT(c) FROM CardSummary c",
                Long.class);
        CountCardSummariesResult response = new CountCardSummariesResult(
                ((Long) jpaQuery.getSingleResult()).intValue());
        logger.info("returning {}", response);
        return response;
    }
}
