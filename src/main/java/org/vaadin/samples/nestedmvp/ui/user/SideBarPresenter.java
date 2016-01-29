package org.vaadin.samples.nestedmvp.ui.user;

import org.vaadin.samples.nestedmvp.api.Presenter;
import org.vaadin.samples.nestedmvp.api.messaging.MessageHandler;
import org.vaadin.samples.nestedmvp.api.messaging.MessageHandlerAction;
import org.vaadin.samples.nestedmvp.ui.user.messages.ContentViewChangedEvent;

public class SideBarPresenter extends Presenter<SideBarView> {

    public SideBarPresenter(Presenter<?> parent) {
        super(parent);
    }

    @MessageHandler
    public MessageHandlerAction onContentViewChangedEvent(ContentViewChangedEvent event) {
        getView().selectNavigationButton(event.request);
        return MessageHandlerAction.PROPAGATE;
    }
}
