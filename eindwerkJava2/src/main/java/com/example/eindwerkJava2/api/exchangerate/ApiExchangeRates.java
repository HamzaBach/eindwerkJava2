package com.example.eindwerkJava2.api.exchangerate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor
public class ApiExchangeRates {
    private final String api_key = "c2625a1a3c71cc4e376b012a";//Requested via email: benibouhayie@gmail.com on website:https://www.exchangerate-api.com/docs/standard-requests

    public ExchangeRates getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String API_URL_GET_EXCHANGERATES = "https://v6.exchangerate-api.com/v6/"+api_key+"/latest/"+baseCurrency;
        HttpRequest requestExchangeRates = HttpRequest.newBuilder().GET().
                header("accept", "application/json").
                uri(URI.create(API_URL_GET_EXCHANGERATES)).build();
        HttpResponse<String> responseExchangeRates = client.send(requestExchangeRates,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRates currentExchangeRates = mapper.readValue(responseExchangeRates.body(), new TypeReference<ExchangeRates>(){});
        //System.out.println(responseExchangeRates.body());
        return currentExchangeRates;
    }

    public Double getCurrentExchangeRateFromTo(String fromCurrency, String toCurrency) throws IOException, InterruptedException {
        ExchangeRates exchangeRates = getExchangeRates(fromCurrency);
        return exchangeRates.getConversionRates().get(toCurrency);
    }

}
