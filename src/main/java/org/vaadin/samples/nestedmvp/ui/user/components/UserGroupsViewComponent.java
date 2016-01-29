package org.vaadin.samples.nestedmvp.ui.user.components;

import org.vaadin.samples.nestedmvp.api.AbstractViewComponent;

import com.vaadin.ui.Label;
import org.vaadin.samples.nestedmvp.ui.user.UserGroupsView;

public class UserGroupsViewComponent extends AbstractViewComponent implements UserGroupsView {

    public UserGroupsViewComponent() {
        setCompositionRoot(new Label("User Groups"));
    }
}
