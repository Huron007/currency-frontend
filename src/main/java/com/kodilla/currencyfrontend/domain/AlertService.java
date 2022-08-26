package com.kodilla.currencyfrontend.domain;

import com.kodilla.currencyfrontend.client.APIClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlertService {

    private Set<Alert> set;
    private static AlertService service;

    private AlertService(){
        set = new HashSet<>();
        List<Alert> list = APIClient.getAlertList();
        for (Alert currency: list) {
            if(currency.isActive()){
                set.add(currency);
            }
        }
    }

    public static AlertService getInstance(){
        if(service == null){
            return new AlertService();
        }
        return service;
    }

    public void addAlert(Alert alert){
        this.set.add(alert);
    }

    public void deleteAlert(Alert alert){
        this.set.remove(alert);
    }

    public Set<Alert> getSet(){
        return set;
    }
}
