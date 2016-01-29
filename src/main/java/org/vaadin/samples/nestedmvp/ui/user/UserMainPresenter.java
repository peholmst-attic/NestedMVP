package org.vaadin.samples.nestedmvp.ui.user;

import org.vaadin.samples.nestedmvp.api.Presenter;
import org.vaadin.samples.nestedmvp.api.PresenterViewFactory;
import org.vaadin.samples.nestedmvp.api.messaging.MessageHandler;
import org.vaadin.samples.nestedmvp.ui.user.messages.ContentViewChangedEvent;
import org.vaadin.samples.nestedmvp.ui.user.messages.ContentViewNavigationRequest;

public class UserMainPresenter extends Presenter<UserMainView> {

    private final PresenterViewFactory.PresenterView<SideBarView, SideBarPresenter> sideBar;
    private final PresenterViewFactory.PresenterView<UserGroupsView, UserGroupsPresenter> userGroups;
    private final PresenterViewFactory.PresenterView<UserProfileView, UserProfilePresenter> userProfile;

    public UserMainPresenter() {
        sideBar = PresenterViewFactory.createPresenterView(SideBarPresenter.class, this);
        userGroups = PresenterViewFactory.createPresenterView(UserGroupsPresenter.class, this);
        userProfile = PresenterViewFactory.createPresenterView(UserProfilePresenter.class, this);
    }

    @Override
    protected void initializeView() {
        getView().setSideBarView(sideBar.getView());
    }

    @MessageHandler
    public void onContentViewNavigationRequest(ContentViewNavigationRequest navigationRequest) {
        switch (navigationRequest) {
        case SHOW_USER_GROUPS:
            getView().setContentView(userGroups.getView());
            break;
        case SHOW_USER_PROFILE:
            getView().setContentView(userProfile.getView());
            break;
        }
        sendMessage(new ContentViewChangedEvent(navigationRequest));
    }
}
