package com.example.bank.service;

import com.example.bank.entity.Forex;
import com.example.bank.repository.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.math.MathContext;
import java.util.NoSuchElementException;

@Service
public class ForexServiceImpl implements ForexService {

    private final ForexRepository forexRepository;

    @Autowired
    public ForexServiceImpl(ForexRepository forexRepository) {
        this.forexRepository = forexRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        // Retrieve the Forex records for both currencies.
        Forex fromForex = forexRepository.findByCurrency(fromCurrency)
                .orElseThrow(() -> new NoSuchElementException("Forex rate not found for currency: " + fromCurrency));
        Forex toForex = forexRepository.findByCurrency(toCurrency)
                .orElseThrow(() -> new NoSuchElementException("Forex rate not found for currency: " + toCurrency));

        // Calculate the conversion factor: toRate / fromRate.
        // Use DECIMAL128 for sufficient precision.
        BigDecimal conversionFactor = toForex.getExchangeRate()
                .divide(fromForex.getExchangeRate(), MathContext.DECIMAL128);
        return amount.multiply(conversionFactor, MathContext.DECIMAL128);
    }
    @Override
    public Forex createForex(Forex forex) {
        return forexRepository.save(forex);
    }

    @Override
    public Forex getForexById(Long id) {
        return forexRepository.findById(id).orElse(null);
    }

    @Override
    public List<Forex> getAllForex() {
        return forexRepository.findAll();
    }

    @Override
    public Forex updateForex(Long id, Forex updatedForex) {
        Forex existingForex = forexRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forex not found"));

        existingForex.setCurrency(updatedForex.getCurrency());
        existingForex.setExchangeRate(updatedForex.getExchangeRate());
        existingForex.setTimestamp(LocalDateTime.now());

        return forexRepository.save(existingForex);
    }

    @Override
    @Transactional
    public void updateForexRates(Map<String, BigDecimal> rates) {
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
            String currency = entry.getKey();
            BigDecimal rate = entry.getValue();
            Optional<Forex> optionalForex = forexRepository.findByCurrency(currency);
            Forex forex;
            if (optionalForex.isPresent()) {
                forex = optionalForex.get();
            } else {
                forex = new Forex();
                forex.setCurrency(currency);
            }
            forex.setExchangeRate(rate);
            forex.setTimestamp(now);
            forexRepository.save(forex);
        }
    }

    @Override
    public void deleteForex(Long id) {
        forexRepository.deleteById(id);
    }
}
