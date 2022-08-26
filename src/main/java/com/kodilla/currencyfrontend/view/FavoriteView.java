package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.client.APIClient;
import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.domain.Currency;
import com.kodilla.currencyfrontend.domain.FavoriteService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("/favorite")
@RouteAlias("favorites")
public class FavoriteView extends VerticalLayout {

    private Grid<Currency> grid = new Grid<>(Currency.class);
    private Grid<Currency> cryptoGrid = new Grid<>(Currency.class);
    private FavoriteService service = FavoriteService.getInstance();

    public FavoriteView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        setGrid(grid, false);
        add(grid);
        setGrid(cryptoGrid, true);
        add(cryptoGrid);
    }

    private void setGrid(Grid<Currency> gridToSet, boolean crypto){
        gridToSet.setColumns("code", "exchangeRate", "effectiveDate");
        gridToSet.addColumn(
                new ComponentRenderer<>(Button::new, (button, currency) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        this.removeFavorite(currency);
                        gridToSet.getDataProvider().refreshAll();
                        Notification.show(currency.getCode() + " removed from favorites.");
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Remove");
        gridToSet.setHeight("250px");
        if(!crypto){
            gridToSet.setItems(service.getSet());
        } else {
            gridToSet.setItems(service.getCryptoSet());
        }
    }

    public void removeFavorite(Currency currency){
        service.removeCurrency(currency);
        APIClient.deleteFavoriteCurrency(currency);
    }
}
