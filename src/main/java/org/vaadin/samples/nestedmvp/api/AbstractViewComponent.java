package org.vaadin.samples.nestedmvp.api;

import org.vaadin.samples.nestedmvp.api.messaging.Message;
import org.vaadin.samples.nestedmvp.api.messaging.MessageConsumer;
import org.vaadin.samples.nestedmvp.api.messaging.MessageProducerDelegate;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

public abstract class AbstractViewComponent extends CustomComponent implements View {

    private final MessageProducerDelegate messageProducerDelegate = new MessageProducerDelegate(this);

    @Override
    public void addMessageConsumer(MessageConsumer messageConsumer) {
        messageProducerDelegate.addMessageConsumer(messageConsumer);
    }

    @Override
    public void removeMessageConsumer(MessageConsumer messageConsumer) {
        messageProducerDelegate.removeMessageConsumer(messageConsumer);
    }

    protected void sendMessage(Message message) {
        messageProducerDelegate.sendMessage(message);
    }

    @Override
    public void executeAndPush(Runnable operation) {
        final UI ui = getUI();
        if (ui == null) {
            throw new IllegalStateException("View not attached to UI");
        }
        ui.access(operation);
    }
}
