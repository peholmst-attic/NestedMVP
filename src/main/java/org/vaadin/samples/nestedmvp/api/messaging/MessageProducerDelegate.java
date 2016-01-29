package org.vaadin.samples.nestedmvp.api.messaging;

import java.io.Serializable;
import java.util.*;

public class MessageProducerDelegate implements MessageProducer, Serializable {

    private final MessageProducer owner;

    private final Map<MessageConsumer, Object> messageConsumers = new WeakHashMap<>();

    public MessageProducerDelegate(MessageProducer owner) {
        this.owner = owner;
    }

    @Override
    public void addMessageConsumer(MessageConsumer messageConsumer) {
        synchronized (messageConsumers) {
            messageConsumers.put(Objects.requireNonNull(messageConsumer), null);
        }
    }

    @Override
    public void removeMessageConsumer(MessageConsumer messageConsumer) {
        synchronized (messageConsumers) {
            messageConsumers.remove(Objects.requireNonNull(messageConsumer));
        }
    }

    public void sendMessage(Message message) {
        sendMessage(owner, message);
    }

    public void sendMessage(MessageProducer sender, Message message) {
        Set<MessageConsumer> consumersCopy;
        synchronized (messageConsumers) {
            consumersCopy = new HashSet<>(messageConsumers.keySet());
        }
        consumersCopy.forEach(consumer -> consumer.onMessage(sender, message));
    }
}
