package org.vaadin.samples.nestedmvp.api;

import java.lang.reflect.ParameterizedType;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.samples.nestedmvp.api.messaging.*;

public abstract class Presenter<V extends View> implements MessageConsumer, MessageProducer {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final MessageConsumerDelegate messageConsumerDelegate = new MessageConsumerDelegate(this);
    private final MessageProducerDelegate messageProducerDelegate = new MessageProducerDelegate(this);
    private final Presenter<?> parent;
    private final Set<Presenter<?>> children = new HashSet<>();
    private V view;

    public Presenter() {
        parent = null;
        logger.debug("Creating presenter without a parent");
    }

    public Presenter(Presenter<?> parent) {
        this.parent = Objects.requireNonNull(parent);
        logger.debug("Creating presenter with parent [{}]", parent);
        parent.children.add(this);
        parent.addMessageConsumer(this);
    }

    public final void setView(V view) {
        Objects.requireNonNull(view);
        if (this.view != null) {
            logger.debug("Detaching from existing view [{}]", this.view);
            this.view.removeMessageConsumer(this);
            detachView();
        }
        logger.debug("Attaching to view [{}]", view);
        this.view = view;
        this.view.addMessageConsumer(this);
        initializeView();
    }

    protected final V getView() {
        if (view == null) {
            throw new IllegalStateException("No view has been set");
        }
        return view;
    }

    protected void detachView() {
    }

    protected void initializeView() {
    }

    @SuppressWarnings("unchecked")
    protected Class<V> getViewClass() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
            if (pt.getActualTypeArguments().length > 0 && pt.getActualTypeArguments()[0] instanceof Class
                && View.class.isAssignableFrom((Class) pt.getActualTypeArguments()[0])) {
                return (Class<V>) pt.getActualTypeArguments()[0];
            }
        }
        throw new UnsupportedOperationException(
            "Could not deduce the View class by introspection. Please override this method.");
    }

    protected final Set<Presenter<?>> getSubPresenters() {
        return Collections.unmodifiableSet(children);
    }

    protected final Optional<Presenter<?>> getParent() {
        return Optional.of(parent);
    }

    @Override
    public void onMessage(MessageProducer sender, Message message) {
        MessageHandlerAction action = messageConsumerDelegate.onMessage(sender, message);
        if (action.equals(MessageHandlerAction.PROPAGATE)) {
            if (parent != null && !sender.equals(parent)) {
                logger.debug("Propagating message [{}] to parent [{}]", message, parent);
                parent.onMessage(this, message);
            }
            logger.debug("Propagating message [{}] to children", message);
            children.stream().filter(child -> !child.equals(sender))
                .forEach(child -> child.onMessage(Presenter.this, message));
        }
    }

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
}
