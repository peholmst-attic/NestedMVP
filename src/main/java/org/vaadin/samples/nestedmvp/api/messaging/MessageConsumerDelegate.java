package org.vaadin.samples.nestedmvp.api.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MessageConsumerDelegate implements Serializable {

    private final Logger logger;

    private final MessageConsumer owner;

    public MessageConsumerDelegate(MessageConsumer owner) {
        this.owner = owner;
        this.logger = LoggerFactory.getLogger(owner.getClass());
    }

    public MessageHandlerAction onMessage(MessageProducer sender, Message message) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(sender);
        logger.debug("Received message [{}] from [{}], looking for handler", message, sender);
        Class<?> clazz = owner.getClass();
        try {
            while (clazz != Object.class) {
                for (Method m : clazz.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(MessageHandler.class) && m.getParameterCount() == 1
                        && m.getParameterTypes()[0].isAssignableFrom(message.getClass())) {
                        logger.debug("Found handler [{}]", m.getName());
                        m.setAccessible(true);
                        try {
                            MessageHandlerAction action;
                            Object result = m.invoke(owner, message);
                            if (result instanceof MessageHandlerAction) {
                                action = (MessageHandlerAction) result;
                            } else {
                                action = MessageHandlerAction.HANDLED;
                            }
                            if (logger.isDebugEnabled()) {
                                if (action.equals(MessageHandlerAction.HANDLED)) {
                                    logger.debug("Message [{}] handled by [{}]", message, m.getName());
                                } else {
                                    logger.debug("Propagating message [{}] as requested by handler [{}]", message, m.getName());
                                }
                            }
                            return action;
                        } catch (InvocationTargetException ex) {
                            throw ex.getTargetException();
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (Throwable ex) {
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException("Error while handling message", ex);
            }
        }
        logger.debug("Found no handler for message [{}], propagating", message);
        return MessageHandlerAction.PROPAGATE;
    }
}
