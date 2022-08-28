package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.client.APIClient;
import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.components.NavigationBar;
import com.kodilla.currencyfrontend.domain.Code;
import com.kodilla.currencyfrontend.domain.Currency;
import com.kodilla.currencyfrontend.domain.CurrencyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;
import java.util.stream.Collectors;

@Route("/calculator")
@RouteAlias("calculator")
public class CalculatorView extends VerticalLayout {

    private ComboBox<String> currencyBoxOne = new ComboBox<>();
    private ComboBox<String> currencyBoxTwo = new ComboBox<>();
    CurrencyService service = CurrencyService.getInstance();

    public CalculatorView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        setup();
    }

    private void setup() {
        List<String> list = APIClient.getLatestCurrencyList(false).stream().map(Currency::getCode).collect(Collectors.toList());
        list.addAll(APIClient.getLatestCryptoCurrencyList(true).stream().map(Currency::getCode).collect(Collectors.toList()));
        currencyBoxOne.setItems(list);
        currencyBoxTwo.setItems(list);

        Button button = new Button("Calculate");
        NumberField numberField = new NumberField();
        numberField.setWidth("165px");
        numberField.setPlaceholder("Currency amount");

        TextField resultField = new TextField();
        resultField.setRequired(true);

        button.addClickListener(e -> {
            resultField.setValue(APIClient.calculate(codeConversion(currencyBoxOne.getValue()), codeConversion(currencyBoxTwo.getValue()), numberField.getValue()).toString());
            currencyBoxOne.setValue(null);
            currencyBoxTwo.setValue(null);
            numberField.clear();
            numberField.setPlaceholder("Currency amount");
        });

        HorizontalLayout layout = new HorizontalLayout();

        layout.add(currencyBoxOne, numberField, currencyBoxTwo, button, resultField);
        layout.getStyle().set("margin", "auto");
        add(layout);
    }

    public String codeConversion(String value){
        value = value.replaceAll("\\s", "_");
        Code code = Code.valueOf(value);
        return code.name;
    }
}
