package org.vaadin.samples.nestedmvp.ui.user;

import org.vaadin.samples.nestedmvp.api.Presenter;

public class UserProfilePresenter extends Presenter<UserProfileView> {

    public UserProfilePresenter(Presenter<?> parent) {
        super(parent);
    }
}
