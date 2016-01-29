package org.vaadin.samples.nestedmvp.ui.user;

import org.vaadin.samples.nestedmvp.api.View;

public interface UserMainView extends View {

    void setSideBarView(SideBarView sideBarView);

    void setContentView(View view);
}
