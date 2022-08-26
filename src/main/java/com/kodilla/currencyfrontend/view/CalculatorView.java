package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.components.NavigationBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("/calculator")
@RouteAlias("calculator")
public class CalculatorView extends VerticalLayout {

    public CalculatorView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
    }
}
