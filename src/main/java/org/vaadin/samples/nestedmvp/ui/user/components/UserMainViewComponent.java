package org.vaadin.samples.nestedmvp.ui.user.components;

import org.vaadin.samples.nestedmvp.api.AbstractViewComponent;
import org.vaadin.samples.nestedmvp.api.View;

import com.vaadin.ui.HorizontalSplitPanel;
import org.vaadin.samples.nestedmvp.ui.user.SideBarView;
import org.vaadin.samples.nestedmvp.ui.user.UserMainView;

public class UserMainViewComponent extends AbstractViewComponent implements UserMainView {

    private HorizontalSplitPanel splitPanel;

    public UserMainViewComponent() {
        setSizeFull();
        splitPanel = new HorizontalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(200, Unit.PIXELS);
        setCompositionRoot(splitPanel);
    }

    @Override
    public void setSideBarView(SideBarView sideBarView) {
        splitPanel.setFirstComponent(sideBarView);
    }

    @Override
    public void setContentView(View view) {
        splitPanel.setSecondComponent(view);
    }
}
