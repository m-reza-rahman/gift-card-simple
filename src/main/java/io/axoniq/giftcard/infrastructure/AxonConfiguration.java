package io.axoniq.giftcard.infrastructure;

import io.axoniq.giftcard.query.CardSummaryProjection;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfiguration {

    /* Using tracking processors for our read model, which will store their tokens. */
    @Autowired
    public void configure(EventHandlingConfiguration configuration) {
        configuration.registerTrackingProcessor(CardSummaryProjection.class.getPackage().getName());
    }

    /* A persistent event bus (event store) for event sourcing in our command model. */
    @Bean
    @Primary
    public EventBus eventBus(EventStorageEngine eventStorageEngine) {
        return new EmbeddedEventStore(eventStorageEngine);
    }

    /* A non-persistent event bus to push messages from our query model. */
    @Bean
    @Qualifier("queryUpdates")
    public EventBus queryUpdateEventBus() {
        return new SimpleEventBus();
    }

    /* We want a JSON serializer for better readability */
    @Qualifier("eventSerializer")
    @Bean
    public Serializer eventSerializer() {
        return new JacksonSerializer();
    }
}
