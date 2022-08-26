package com.kodilla.currencyfrontend.components;

import com.kodilla.currencyfrontend.view.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


public class NavigationBar extends AppLayout {

    public NavigationBar(VerticalLayout layout) {
        H1 title = new H1("Currency App");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();

        FetchDataButton button = new FetchDataButton();
        button.getStyle()
                .set("right", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");
        addToNavbar(title, tabs, button);
        layout.add(this);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(
                createTab("Home", MainView.class),
                createTab("Currency Table", CurrencyTableView.class),
                createTab("Favorites", FavoriteView.class),
                createTab("Alerts", AlertsView.class),
                createTab("Currency Calculator", CalculatorView.class)
        );
        return tabs;
    }

    private Tab createTab(String viewName, Class<?> className) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute((Class<? extends Component>) className);
        link.setTabIndex(-1);
        return new Tab(link);
    }
}
