# Simple Gift Card

This simple application demonstrates the CQRS (Command Query Responsibility Segregation) and Event Sourcing patterns using the Axon Java Framework. The domain used is gift cards. This application is demoed during this [presentation](https://speakerdeck.com/reza_rahman/what-is-cqrs-plus-event-sourcing-and-why-should-java-developers-care). Gift cards themselves are event sourced aggregate entities that constitute the write model, execute commands and emit events. The read model handles events, constructs several materialized views and powers several queries.   

The Maven based application only requires Java SE 8 (or up). You should be able to open the application in any Maven capable IDE. Once built, you can run the application through the following command.

```
java -jar gift-card-simple-1.0.jar
```

Once the application is up and running, you can access it via http://localhost:8080.

![UI](/screenshots/ui.jpg)

The application has a small GUI running on port 8080 (implemented using [Vaadin](https://vaadin.com/)) where you can issue single cards, bulk issue cards, redeem cards,
and view a list of cards.

jdbc:h2:mem:testdb sa

 
