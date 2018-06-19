package io.axoniq.giftcard.ui;

import com.vaadin.data.provider.CallbackDataProvider;
import io.axoniq.giftcard.query.CardSummariesUpdatedEvent;
import io.axoniq.giftcard.query.CardSummary;
import io.axoniq.giftcard.query.CountCardSummariesQuery;
import io.axoniq.giftcard.query.CountCardSummariesResult;
import io.axoniq.giftcard.query.FindCardSummariesQuery;
import io.axoniq.giftcard.query.FindCardSummariesResult;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardSummaryDataProvider extends CallbackDataProvider<CardSummary, Void> {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SubscribingEventProcessor subscribingEventProcessor;

    public CardSummaryDataProvider(QueryGateway queryGateway, EventBus queryUpdateEventBus) {
        super(q -> {
            FindCardSummariesQuery query = new FindCardSummariesQuery(q.getOffset(), q.getLimit());
            FindCardSummariesResult result = queryGateway.send(query, FindCardSummariesResult.class).join();

            return result.getCardSummaries().stream();
        },
                q -> {
                    CountCardSummariesQuery query = new CountCardSummariesQuery();
                    CountCardSummariesResult result = queryGateway.send(query, CountCardSummariesResult.class).join();

                    return result.getCount();
                }
        );

        EventListener eventListener = new AnnotationEventListenerAdapter(this);
        subscribingEventProcessor = new SubscribingEventProcessor(UUID.randomUUID().toString(),
                new SimpleEventHandlerInvoker(eventListener), queryUpdateEventBus);
        subscribingEventProcessor.start();
    }

    public void shutDown() {
        subscribingEventProcessor.shutDown();
    }

    @EventHandler
    public void on(CardSummariesUpdatedEvent cardSummariesUpdatedEvent) {
        logger.info("Received {}", cardSummariesUpdatedEvent);

        refreshAll();
    }
}
