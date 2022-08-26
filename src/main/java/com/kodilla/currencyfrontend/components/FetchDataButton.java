package com.kodilla.currencyfrontend.components;

import com.kodilla.currencyfrontend.client.APIClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;


public class FetchDataButton extends Button {

    public FetchDataButton() {
        setText("Update Data");
        setIcon(new Icon(VaadinIcon.DOWNLOAD));
        addClickListener(event -> this.getUI().ifPresent(action -> refresh()));
    }

    public void refresh(){
        APIClient.updateTable();
        UI.getCurrent().getPage().reload();
    }
}
