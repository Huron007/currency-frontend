package com.kodilla.currencyfrontend.domain;

import com.kodilla.currencyfrontend.client.APIClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteService {

    private static Set<Currency> set;
    private static Set<Currency> cryptoSet;
    private List<Currency> list;
    private List<Currency> cryptoList;
    private static FavoriteService service;

    private FavoriteService(){
        set = new HashSet<>();
        cryptoSet = new HashSet<>();
        list = APIClient.getFavoriteCurrencyList();
        cryptoList = APIClient.getFavoriteCryptoCurrencyList();
        for(Currency currency: list){
            set.add(currency);
        }
        for(Currency currency: cryptoList){
            cryptoSet.add(currency);
        }
    }

    public static FavoriteService getInstance(){
        if(service == null){
            return  new FavoriteService();
        }
        return service;
    }

    public void addCurrency(Currency currency){
        set.add(currency);
    }

    public void removeCurrency(Currency currency){
        this.set.remove(currency);
    }

    public Set<Currency> getSet(){
        return set;
    }

    public Set<Currency> getCryptoSet(){
        return cryptoSet;
    }

    public List<Currency> getList(){
        return list;
    }
}
