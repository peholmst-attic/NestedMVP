package org.vaadin.samples.nestedmvp.api.messaging;

@FunctionalInterface
public interface MessageConsumer {

    void onMessage(MessageProducer sender, Message message);
}
