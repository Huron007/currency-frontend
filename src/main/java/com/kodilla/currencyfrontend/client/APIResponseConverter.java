package com.kodilla.currencyfrontend.client;

import com.kodilla.currencyfrontend.domain.Alert;
import com.kodilla.currencyfrontend.domain.Code;
import com.kodilla.currencyfrontend.domain.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class APIResponseConverter {

    public List<Currency> convertCurrencyResponseToCurrencyList(Currency[] lists, boolean crypto){
        List<Currency> list = new ArrayList<>();
        for (Currency currency : lists) {
            if (!crypto) {
                list.add(
                        new Currency(
                                currency.getId(),
                                currency.getName(),
                                currency.getCode(),
                                currency.getExchangeRate(),
                                currency.getEffectiveDate()
                        )
                );
            } else {
                list.add(
                        new Currency(
                                currency.getId(),
                                currency.getCode(),
                                currency.getName(),
                                currency.getExchangeRate(),
                                currency.getEffectiveDate()
                        )
                );
            }
        }
        return list;
    }

    public List<Currency> convertToFavoriteCurrencyList(Currency[] lists){
        List<Currency> list = new ArrayList<>(APIClient.getLatestCurrencyList(false));
        return getCurrencies(lists, list, false);
    }

    public List<Currency> convertToFavoriteCryptoCurrencyList(Currency[] lists){
        List<Currency> list = new ArrayList<>(APIClient.getLatestCryptoCurrencyList(true));
        return getCurrencies(lists, list, true);
    }

    private List<Currency> getCurrencies(Currency[] lists, List<Currency> list, boolean crypto) {
        List<Currency> responseList = new ArrayList<>();
        for (Currency currency : lists) {
            responseList.add(
                    new Currency(
                            currency.getId(),
                            currency.getName(),
                            currency.getCode(),
                            currency.getExchangeRate(),
                            currency.getEffectiveDate()
                    )
            );
        }
        List<Currency> filteredList;
        if(!crypto){
            filteredList = list.stream().filter(e -> responseList.stream().map(Currency::getCode).anyMatch(a -> e.getCode().equals(a))).collect(Collectors.toList());
            for(Currency currency: filteredList){
                for(Currency currency1: responseList){
                    if (currency.getCode().equals(currency1.getCode())){
                        currency.setId(currency1.getId());
                    }
                }
            }
        } else {
            filteredList = list.stream().filter(e -> responseList.stream().map(Currency::getCode).anyMatch(a -> e.getName().equals(a))).collect(Collectors.toList());
            for(Currency currency: filteredList){
                for(Currency currency1: responseList){
                    if (currency.getName().equals(currency1.getCode())){
                        currency.setId(currency1.getId());
                    }
                }
            }
        }
        return filteredList;
    }

    public List<Alert> convertToAlertList(Alert[] alerts){
        List<Alert> list = new ArrayList<>();
        Code value;
        String name;
        for (Alert alert : alerts) {
            value = Code.findByName(alert.getCode());
            name = value.toString();
            name = name.replaceAll("_", " ");
            list.add(
                    new Alert(
                            alert.getId(),
                            alert.getName(),
                            name,
                            alert.getTrackedMargin(),
                            alert.isActive()
                    )
            );
        }
        return list;
    }
}
