package org.vaadin.samples.nestedmvp.ui;

import javax.servlet.annotation.WebServlet;

import org.vaadin.samples.nestedmvp.api.PresenterViewFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.samples.nestedmvp.ui.user.UserMainPresenter;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        setContent(PresenterViewFactory.createPresenterView(UserMainPresenter.class).getView());
    }

    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }
}
