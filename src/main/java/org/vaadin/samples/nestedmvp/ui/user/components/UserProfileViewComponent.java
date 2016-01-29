package org.vaadin.samples.nestedmvp.ui.user.components;

import org.vaadin.samples.nestedmvp.api.AbstractViewComponent;

import com.vaadin.ui.Label;
import org.vaadin.samples.nestedmvp.ui.user.UserProfileView;

public class UserProfileViewComponent extends AbstractViewComponent implements UserProfileView {

    public UserProfileViewComponent() {
        setCompositionRoot(new Label("User Profile"));
    }
}
