package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.components.MainViewForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@Route()
@RouteAlias("home")
public class MainView extends VerticalLayout {

    public MainView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        MainViewForm mainViewForm = new MainViewForm();
        add(mainViewForm);
    }
}
