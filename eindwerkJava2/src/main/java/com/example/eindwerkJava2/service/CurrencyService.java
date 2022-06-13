package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.api.exchangerate.ApiExchangeRates;
import com.example.eindwerkJava2.model.Currency;
import com.example.eindwerkJava2.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository=currencyRepository;
    }

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public void saveCurrency(Currency currency){
        currencyRepository.save(currency);
    }

    public Currency findById(long currencyId){
        return currencyRepository.findById(currencyId).get();
    }

    public Double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException, InterruptedException {
        ApiExchangeRates exchangeRates = new ApiExchangeRates();
        return exchangeRates.getCurrentExchangeRateFromTo(baseCurrency,targetCurrency);
    }
    public Currency findByCurrency(String currencyName){
        return currencyRepository.findByCurrency(currencyName);
    }
}
