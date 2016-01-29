package org.vaadin.samples.nestedmvp.ui.user;

import org.vaadin.samples.nestedmvp.api.View;
import org.vaadin.samples.nestedmvp.ui.user.messages.ContentViewNavigationRequest;

public interface SideBarView extends View {

    void selectNavigationButton(ContentViewNavigationRequest message);
}
