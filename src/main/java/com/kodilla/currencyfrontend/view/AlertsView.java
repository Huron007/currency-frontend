package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.client.APIClient;
import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.domain.Alert;
import com.kodilla.currencyfrontend.domain.AlertService;
import com.kodilla.currencyfrontend.domain.Code;
import com.kodilla.currencyfrontend.domain.Currency;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;
import java.util.stream.Collectors;

@Route("/alerts")
@RouteAlias("alerts")
public class AlertsView extends VerticalLayout {

    private Grid<Alert> grid = new Grid<>(Alert.class, false);
    private AlertService service = AlertService.getInstance();
    private ComboBox<String> comboBox = new ComboBox<>();

    public AlertsView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        setInputBar();
        setGrid();
        add(grid);
    }

    private void setGrid(){
        grid.setColumns("code", "trackedMargin");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, alert) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        service.deleteAlert(alert);
                        APIClient.deleteAlert(alert);
                        grid.getDataProvider().refreshAll();
                        Notification.show(alert.getCode() + " alert was deleted.");
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Delete");
        grid.setItems(service.getSet());
    }

    public void createAlert(String code, Double targetMargin){
        code = code.replaceAll("\\s", "_");
        Code value = Code.valueOf(code);
        Alert alert = new Alert(null, null, value.name, targetMargin, true);
        APIClient.createAlert(alert);
    }

    public void setInputBar(){
        List<String> list = APIClient.getLatestCurrencyList(false).stream().map(Currency::getCode).collect(Collectors.toList());
        list.addAll(APIClient.getLatestCryptoCurrencyList(true).stream().map(Currency::getCode).collect(Collectors.toList()));
        comboBox.setItems(list);

        Button button = new Button("Create Alert");
        NumberField numberField = new NumberField();
        numberField.setWidth("165px");
        numberField.setPlaceholder("Enter Target Margin");

        button.addClickListener(e -> {
            createAlert(comboBox.getValue(), numberField.getValue());
            Notification.show("Alert for " + comboBox.getValue() + " with target margin " + numberField.getValue() + " was created.");
            comboBox.setValue(null);
            numberField.clear();
            numberField.setPlaceholder("Enter Target Margin");
            grid.getDataProvider().refreshAll();
        });

        HorizontalLayout layout = new HorizontalLayout();

         layout.add(comboBox, numberField, button);
         layout.getStyle().set("margin", "auto");
         add(layout);
    }
}
