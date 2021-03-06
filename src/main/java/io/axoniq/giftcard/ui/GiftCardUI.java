package io.axoniq.giftcard.ui;

import com.vaadin.annotations.Push;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import io.axoniq.giftcard.command.IssueCommand;
import io.axoniq.giftcard.command.RedeemCommand;
import io.axoniq.giftcard.query.CardSummary;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringUI
@Push
public class GiftCardUI extends UI {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final EventBus queryUpdateEventBus;

    private CardSummaryDataProvider cardSummaryDataProvider;

    public GiftCardUI(CommandGateway commandGateway, QueryGateway queryGateway, @Qualifier("queryUpdates") EventBus queryUpdateEventBus) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.queryUpdateEventBus = queryUpdateEventBus;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout commandBar = new HorizontalLayout();
        commandBar.setSizeFull();
        commandBar.addComponents(issuePanel(), bulkIssuePanel(), redeemPanel());

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(commandBar, summaryGrid());
        layout.setHeight(95, Unit.PERCENTAGE);

        setContent(layout);

        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                Throwable cause = event.getThrowable();
                logger.error("An error occured", cause);
                while (cause.getCause() != null) {
                    cause = cause.getCause();
                }
                Notification.show("Error", cause.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void close() {
        cardSummaryDataProvider.shutDown();
        super.close();
    }

    private Panel issuePanel() {
        TextField id = new TextField("Card ID");
        TextField amount = new TextField("Amount");
        Button submit = new Button("Submit");

        submit.addClickListener(evt -> {
            commandGateway.sendAndWait(new IssueCommand(id.getValue(), Integer.parseInt(amount.getValue())));
            Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
        });

        FormLayout form = new FormLayout();
        form.addComponents(id, amount, submit);
        form.setMargin(true);

        Panel panel = new Panel("Issue single card");
        panel.setContent(form);
        return panel;
    }

    private Panel bulkIssuePanel() {
        TextField number = new TextField("Number");
        TextField amount = new TextField("Amount");
        Button submit = new Button("Submit");

        submit.addClickListener(evt -> {
            for (int i = 0; i < Integer.parseInt(number.getValue()); i++) {
                String id = UUID.randomUUID().toString().substring(0, 11).toUpperCase();
                commandGateway.sendAndWait(new IssueCommand(id, Integer.parseInt(amount.getValue())));
            }
            Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
        });

        FormLayout form = new FormLayout();
        form.addComponents(number, amount, submit);
        form.setMargin(true);

        Panel panel = new Panel("Bulk issue cards");
        panel.setContent(form);
        return panel;
    }

    private Panel redeemPanel() {
        TextField id = new TextField("Card ID");
        TextField amount = new TextField("Amount");
        Button submit = new Button("Submit");

        submit.addClickListener(evt -> {
            commandGateway.sendAndWait(new RedeemCommand(id.getValue(), Integer.parseInt(amount.getValue())));
            Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
        });

        FormLayout form = new FormLayout();
        form.addComponents(id, amount, submit);
        form.setMargin(true);

        Panel panel = new Panel("Redeem card");
        panel.setContent(form);
        return panel;
    }

    private Grid summaryGrid() {
        cardSummaryDataProvider = new CardSummaryDataProvider(queryGateway, queryUpdateEventBus);

        Grid<CardSummary> grid = new Grid<>();
        grid.addColumn(CardSummary::getId).setCaption("Card ID");
        grid.addColumn(CardSummary::getInitialValue).setCaption("Initial value");
        grid.addColumn(CardSummary::getIssuedAt).setCaption("Issued at");
        grid.addColumn(CardSummary::getRemainingValue).setCaption("Remaining value");
        grid.setSizeFull();
        grid.setDataProvider(cardSummaryDataProvider);

        return grid;
    }
}
