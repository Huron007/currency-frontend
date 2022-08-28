package com.kodilla.currencyfrontend.components;

import com.kodilla.currencyfrontend.view.CurrencyDetailView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;

import static com.kodilla.currencyfrontend.client.APIClient.getSingleCurrencyExchangeRate;

public class MainViewForm extends FormLayout {

    public MainViewForm() {
        getStyle().set("margin", "auto").set("width", "35%").set("height", "100px");
        setResponsiveSteps(new FormLayout.ResponsiveStep("0", 6));
        Label euroName = new Label("EURO");
        Label usdName = new Label("USD");
        Label gbpName = new Label("GBP");
        Label euroValue = new Label(getSingleCurrencyExchangeRate("EUR").toString());
        Label usdValue = new Label(getSingleCurrencyExchangeRate("USD").toString());
        Label gbpValue = new Label(getSingleCurrencyExchangeRate("GBP").toString());
        Image euroImage = new Image("https://flagcdn.com/w160/eu.png", "EURO");
        euroImage.setWidth("60px");
        Image usdImage = new Image("https://flagcdn.com/w160/us.png", "USD");
        usdImage.setWidth("60px");
        Image gbpImage = new Image("https://flagcdn.com/w160/gb.png", "GBP");
        gbpImage.setWidth("60px");
        Button euroButton = new Button(euroImage);
        euroButton.addClickListener(
                event -> {
                    CurrencyDetailView.code = "EUR";
                    CurrencyDetailView.listSetup(false);
                    euroButton.getUI().ifPresent(ui -> ui.navigate("currency/EUR/details"));
                }
        );
        euroButton.setWidth("20px");
        Button usdButton = new Button(usdImage);
        usdButton.addClickListener(
                event -> {
                    CurrencyDetailView.code = "USD";
                    CurrencyDetailView.listSetup(false);
                    usdButton.getUI().ifPresent(ui -> ui.navigate("currency/USD/details"));
                }
        );
        Button gbpButton = new Button(gbpImage);
        gbpButton.addClickListener(
                event -> {
                    CurrencyDetailView.code = "GBP";
                    CurrencyDetailView.listSetup(false);
                    gbpButton.getUI().ifPresent(ui -> ui.navigate("currency/GBP/details"));
                }
        );
        add(euroName, usdName, gbpName, euroButton, euroValue, usdButton, usdValue, gbpButton, gbpValue);
        setColspan(euroName, 2);
        setColspan(usdName, 2);
        setColspan(gbpName, 2);
    }
}
