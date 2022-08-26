package com.kodilla.currencyfrontend.components;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ComponentInitializer {

    public ComponentInitializer(VerticalLayout layout) {
        AppInfoForm appInfoForm = new AppInfoForm(layout);
        NavigationBar navigationBar = new NavigationBar(layout);
    }
}
