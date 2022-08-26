package com.kodilla.currencyfrontend.domain;

import com.kodilla.currencyfrontend.client.APIClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CurrencyService {

    private Set<Currency> set;
    private Set<Currency> cryptoSet;
    private static List<Currency> list;
    private static List<Currency> cryptoList;
    private static CurrencyService service;

    private CurrencyService(){
        set = new HashSet<>();
        cryptoSet = new HashSet<>();
        list = APIClient.getLatestCurrencyList(false);
        cryptoList = APIClient.getLatestCryptoCurrencyList(true);
        for (Currency currency: list) {
            set.add(currency);
        }
        for(Currency currency: cryptoList){
            cryptoSet.add(currency);
        }
    }

    public static CurrencyService getInstance(){
        if(service == null){
            return new CurrencyService();
        }
        return service;
    }

    public void addCurrency(Currency currency){
        this.set.add(currency);
    }

    public void addCryptoCurrency(Currency currency){
        this.cryptoSet.add(currency);
    }

    public Set<Currency> getSet(){
        return set;
    }

    public Set<Currency> getCryptoSet(){
        return cryptoSet;
    }

    public static List<Currency> getList(){
        return list;
    }

    public static List<Currency> getCryptoList(){
        return cryptoList;
    }
}
