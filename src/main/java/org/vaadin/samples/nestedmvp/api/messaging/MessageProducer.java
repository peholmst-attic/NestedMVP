package org.vaadin.samples.nestedmvp.api.messaging;

public interface MessageProducer {

    void addMessageConsumer(MessageConsumer messageConsumer);

    void removeMessageConsumer(MessageConsumer messageConsumer);
}
