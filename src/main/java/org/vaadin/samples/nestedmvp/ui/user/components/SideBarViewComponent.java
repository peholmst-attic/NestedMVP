package org.vaadin.samples.nestedmvp.ui.user.components;

import org.vaadin.samples.nestedmvp.api.AbstractViewComponent;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.samples.nestedmvp.ui.user.SideBarView;
import org.vaadin.samples.nestedmvp.ui.user.messages.ContentViewNavigationRequest;

public class SideBarViewComponent extends AbstractViewComponent implements SideBarView {

    private final Button showUserProfile;
    private final Button showUserGroups;

    public SideBarViewComponent() {
        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);

        showUserProfile = new Button("User Profile",
            evt -> sendMessage(ContentViewNavigationRequest.SHOW_USER_PROFILE));
        showUserProfile.setWidth("100%");
        layout.addComponent(showUserProfile);

        showUserGroups = new Button("User Groups",
            evt -> sendMessage(ContentViewNavigationRequest.SHOW_USER_GROUPS));
        showUserGroups.setWidth("100%");
        layout.addComponent(showUserGroups);
    }

    @Override
    public void selectNavigationButton(ContentViewNavigationRequest message) {
        showUserProfile.removeStyleName(ValoTheme.BUTTON_FRIENDLY);
        showUserGroups.removeStyleName(ValoTheme.BUTTON_FRIENDLY);
        switch (message) {
        case SHOW_USER_GROUPS:
            showUserGroups.addStyleName(ValoTheme.BUTTON_FRIENDLY);
            break;
        case SHOW_USER_PROFILE:
            showUserProfile.addStyleName(ValoTheme.BUTTON_FRIENDLY);
            break;
        }
    }
}
