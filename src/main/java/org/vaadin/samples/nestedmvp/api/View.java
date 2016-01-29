package org.vaadin.samples.nestedmvp.api;

import com.vaadin.ui.Component;
import org.vaadin.samples.nestedmvp.api.messaging.MessageProducer;

public interface View extends MessageProducer, Component {

    void executeAndPush(Runnable operation);
}
