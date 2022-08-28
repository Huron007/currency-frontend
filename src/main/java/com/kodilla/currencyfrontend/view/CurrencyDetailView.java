package com.kodilla.currencyfrontend.view;

import com.kodilla.currencyfrontend.client.APIClient;
import com.kodilla.currencyfrontend.components.ComponentInitializer;
import com.kodilla.currencyfrontend.domain.Code;
import com.kodilla.currencyfrontend.domain.Currency;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route("currency/:code/details")
public class CurrencyDetailView extends VerticalLayout implements BeforeEnterObserver {

    public static String code;
    private static List<Currency> list;

    public CurrencyDetailView() {
        ComponentInitializer componentInitializer = new ComponentInitializer(this);
        list = list.stream().sorted(Comparator.comparing(Currency::getExchangeRate)).limit(20).collect(Collectors.toList());

        Chart chart = new Chart(ChartType.AREA);

        final Configuration configuration = chart.getConfiguration();

        Code name = Code.findByName(code);
        assert name != null;
        String currencyName = name.toString();
        currencyName = currencyName.replaceAll("_", " ");
        configuration.setTitle("Exchange rate of " + currencyName + " in last " + list.size() + " exchange rate updates.");

        XAxis xAxis = configuration.getxAxis();
        String[] dateList = new String[list.size()];
        Double[] exchangeRateList = new Double[list.size()];
        list = list.stream().sorted(Comparator.comparing(Currency::getEffectiveDate)).limit(20).collect(Collectors.toList());
        for(int i = 0; i < list.size(); i++){
            dateList[i] = list.get(i).getEffectiveDate().toString();
            exchangeRateList[i] = list.get(i).getExchangeRate();
        }
        xAxis.setCategories(dateList);
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setTitle("PLN");

        configuration.getTooltip().setValueSuffix(" PLN");

        PlotOptionsArea plotOptionsArea = new PlotOptionsArea();
        plotOptionsArea.setStacking(Stacking.NORMAL);
        configuration.setPlotOptions(plotOptionsArea);

        configuration.addSeries(new ListSeries(code, exchangeRateList));

        add(chart);
    }

    public static void listSetup(boolean crypto){
        if(!crypto){
            list = APIClient.getAllCurrenciesWithGivenCode(code, crypto);
        } else {
            list = APIClient.getAllCryptoCurrenciesWithGivenCode(code, crypto);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        code = event.getRouteParameters().get("code").get();
    }
}
