package com.kodilla.currencyfrontend.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AppInfoForm extends Div {

    public AppInfoForm(VerticalLayout layout) {
        layout.getStyle().set("margin", "0").set("padding", "0");
        Label info = new Label("Currency App made as Kodilla project, created by Tomasz Marciniak email: huron3572@gmail.com");
        info.getStyle().set("text-align", "center").set("background-color", "#61C24169").set("margin", "0").set("border-color", "black").set("border", "5px solid").set("width", "100%");
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0",1));
        formLayout.add(info);
        formLayout.setWidth("100%");
        formLayout.getStyle().set("position", "absolute").set("margin", "0").set("bottom", "20px").set("font-size-adjust", "0.66");
        layout.add(formLayout);
    }
}
