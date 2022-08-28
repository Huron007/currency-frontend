package com.kodilla.currencyfrontend.domain;

import lombok.Getter;

@Getter
public enum Code {
    THB("THB"),
    USD("USD"),
    AUD("AUD"),
    HKD("HKD"),
    CAD("CAD"),
    NZD("NZD"),
    SGD("SGD"),
    EUR("EUR"),
    HUF("HUF"),
    CHF("CHF"),
    GBP("GBP"),
    UAH("UAH"),
    JPY("JPY"),
    CZK("CZK"),
    DKK("DKK"),
    ISK("ISK"),
    NOK("NOK"),
    SEK("SEK"),
    HRK("HRK"),
    RON("RON"),
    BGN("BGN"),
    TRY("TRY"),
    ILS("ILS"),
    CLP("CLP"),
    PHP("PHP"),
    MXN("MXN"),
    ZAR("ZAR"),
    BRL("BRL"),
    MYR("MYR"),
    IDR("IDR"),
    INR("INR"),
    KRW("KRW"),
    CNY("CNY"),
    XDR("XDR"),
    //Crypto
    Bitcoin("btc"),
    Ethereum("eth"),
    Tether("usdt"),
    USD_Coin("usdc"),
    BNB( "bnb"),
    Cardano("ada"),
    XRP("xrp"),
    Binance_USD("busd"),
    Solana("sol"),
    Dogecoin("doge");

    public final String name;

    Code(String name) {
        this.name = name;
    }

    public static Code findByName(String name){
        Code result = null;
        for(Code code: values()){
            if(code.getName().equals(name)){
                result = code;
                return result;
            }
        }
        return null;
    }
}
