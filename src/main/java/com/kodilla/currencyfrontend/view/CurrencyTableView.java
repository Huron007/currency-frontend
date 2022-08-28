package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.client.APIClient;
import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.domain.Currency;
import com.kodilla.currencyfrontend.domain.CurrencyService;
import com.kodilla.currencyfrontend.domain.FavoriteService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("/currencyTable")
@RouteAlias("table")
public class CurrencyTableView extends VerticalLayout {

    private Grid<Currency> grid = new Grid<>(Currency.class);
    private Grid<Currency> cryptoGrid = new Grid<>(Currency.class);
    private CurrencyService service = CurrencyService.getInstance();
    private ComboBox<Currency> comboBox = new ComboBox<>();
    private ComboBox<Currency> cryptoComboBox = new ComboBox<>();


    public CurrencyTableView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        setGrid(grid, false);
        setComboBox(comboBox, false);
        add(grid);
        setGrid(cryptoGrid, true);
        setComboBox(cryptoComboBox, true);
        add(cryptoGrid);
    }

    private void addToFavorite(Currency currency, boolean crypto){
        if(crypto){
            Currency cryptoCurrency = new Currency(currency.getId(), currency.getCode(), currency.getName(), currency.getExchangeRate(), currency.getEffectiveDate());
            APIClient.createFavoriteCurrency(cryptoCurrency);
        } else {
            APIClient.createFavoriteCurrency(currency);
        }
    }

    private void setGrid(Grid<Currency> gridToSet, boolean crypto){
        gridToSet.setColumns("code", "exchangeRate", "effectiveDate");
        gridToSet.addColumn(
                new ComponentRenderer<>(Button::new, (button, currency) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        if(!crypto){
                            CurrencyDetailView.code = currency.getCode();
                            CurrencyDetailView.listSetup(crypto);
                            button.getUI().ifPresent(ui -> ui.navigate("currency/"+ currency.getCode() +"/details"));
                        } else {
                            CurrencyDetailView.code = currency.getName();
                            CurrencyDetailView.listSetup(crypto);
                            button.getUI().ifPresent(ui -> ui.navigate("currency/"+ currency.getName() +"/details"));
                        }
                    });
                    button.setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
                })).setHeader("Check details");
        gridToSet.addColumn(
                new ComponentRenderer<>(Button::new, (button, currency) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        this.addToFavorite(currency, crypto);
                        Notification.show(currency.getCode() + " added to favorites.");
                    });
                    button.setIcon(new Icon(VaadinIcon.PLUS));
                })).setHeader("Add to Favorites");
        gridToSet.setHeight("250px");
        if(crypto == false){
            gridToSet.setItems(service.getSet());
        } else {
            gridToSet.setItems(service.getCryptoSet());
        }
    }

    private void setComboBox(ComboBox<Currency> boxToSet, boolean crypto){
        List<Currency> list;
        if(crypto == false){
            list = APIClient.getLatestCurrencyList(crypto);
        } else {
            list = APIClient.getLatestCryptoCurrencyList(crypto);
        }
        boxToSet.setItems(list);
        boxToSet.setItemLabelGenerator(Currency::getCode);

        Button button = new Button("Add to Favorite");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(e -> {
            addToFavorite(boxToSet.getValue(), crypto);
            Notification.show(boxToSet.getValue().getCode() + " added to favorites.");
            boxToSet.setValue(null);
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.addAndExpand(boxToSet);
        layout.add(button);
        layout.setFlexGrow(1, boxToSet);
        layout.getStyle().set("margin", "auto");
        add(layout);
    }
}
