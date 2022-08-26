package com.kodilla.currencyfrontend.client;

import com.kodilla.currencyfrontend.domain.Alert;
import com.kodilla.currencyfrontend.domain.Currency;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class APIClient {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final APIResponseConverter converter = new APIResponseConverter();

    public static URI getUrl(UrlCode urlCode) {
        return getUrl(null, urlCode);
    }

    public static URI getUrl(String code, UrlCode urlCode){
        switch (urlCode){
            case SINGLE_CURRENCY_EXCHANGE_RATE:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/currency/rate/" + code)
                        .build()
                        .encode()
                        .toUri();
            case UPDATE_CURRENCY_TABLE:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/currency/fetch/lastMonth/table/")
                        .build()
                        .encode()
                        .toUri();
            case UPDATE_CRYPTO_CURRENCY_TABLE:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/cryptoCurrency/fetch/topTen")
                        .build()
                        .encode()
                        .toUri();
            case SINGLE_CURRENCY_LIST:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/currency/list/" + code)
                        .build()
                        .encode()
                        .toUri();
            case LATEST_CURRENCY_LIST:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/currency/list/latest")
                        .build()
                        .encode()
                        .toUri();
            case ALERT:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/alert")
                        .build()
                        .encode()
                        .toUri();
            case LATEST_CRYPTO_CURRENCY_LIST:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/cryptoCurrency/list/latest")
                        .build()
                        .encode()
                        .toUri();
            case FAVORITE:
                return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/favorite")
                        .build()
                        .encode()
                        .toUri();
            default:
                return null;
        }
    }

    public static Double getSingleCurrencyExchangeRate(String code){
        try {
            return Objects.requireNonNull(restTemplate.getForObject(getUrl(code, UrlCode.SINGLE_CURRENCY_EXCHANGE_RATE), Double.class));
        } catch (RestClientException e){
            return 1.0;
        }
    }

    public static List<Currency> getLatestCurrencyList(boolean crypto){
        try {
            return converter.convertCurrencyResponseToCurrencyList(Objects.requireNonNull(restTemplate.getForObject(getUrl(UrlCode.LATEST_CURRENCY_LIST), Currency[].class)), crypto);
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static List<Currency> getLatestCryptoCurrencyList(boolean crypto){
        try {
            return converter.convertCurrencyResponseToCurrencyList(Objects.requireNonNull(restTemplate.getForObject(getUrl(UrlCode.LATEST_CRYPTO_CURRENCY_LIST), Currency[].class)), crypto);
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static List<Currency> getAllCurrenciesWithGivenCode(String code, boolean crypto){
        try {
            return converter.convertCurrencyResponseToCurrencyList(Objects.requireNonNull(restTemplate.getForObject(getUrl(code, UrlCode.SINGLE_CURRENCY_LIST), Currency[].class)), crypto);
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static List<Currency> getFavoriteCurrencyList(){
        try{
            return converter.convertToFavoriteCurrencyList(Objects.requireNonNull(restTemplate.getForObject(getUrl(UrlCode.FAVORITE), Currency[].class)));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static List<Currency> getFavoriteCryptoCurrencyList(){
        try{
            return converter.convertToFavoriteCryptoCurrencyList(Objects.requireNonNull(restTemplate.getForObject(getUrl(UrlCode.FAVORITE), Currency[].class)));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static void createFavoriteCurrency(Currency currency){
        try {
            Objects.requireNonNull(restTemplate.postForObject(getUrl(UrlCode.FAVORITE), new HttpEntity<>(currency), Currency.class));
        } catch (RestClientException e){
            new Currency();
        }
    }

    public static void deleteFavoriteCurrency(Currency currency){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/favorite/" + currency.getId())
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e){
            e.printStackTrace();
        }
    }

    public static List<Alert> getAlertList(){
        try {
            return converter.convertToAlertList(Objects.requireNonNull(restTemplate.getForObject(getUrl(UrlCode.ALERT), Alert[].class)));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public static void updateTable(){
        restTemplate.getForObject(getUrl(UrlCode.UPDATE_CURRENCY_TABLE), Object.class);
        restTemplate.getForObject(getUrl(UrlCode.UPDATE_CRYPTO_CURRENCY_TABLE), Object.class);
    }
}
