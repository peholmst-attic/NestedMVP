package org.vaadin.samples.nestedmvp.ui.user.messages;

import org.vaadin.samples.nestedmvp.api.messaging.Message;

public class ContentViewChangedEvent implements Message {
    public final ContentViewNavigationRequest request;

    public ContentViewChangedEvent(ContentViewNavigationRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", getClass().getSimpleName().toUpperCase(), request);
    }
}
