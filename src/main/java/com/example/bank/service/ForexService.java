package com.example.bank.service;

import com.example.bank.entity.Forex;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
public interface ForexService {
    /**
     * Converts the given amount from one currency to another.
     *
     * @param amount       the amount to convert
     * @param fromCurrency the ISO code of the original currency (e.g., "USD", "ILS", "EUR")
     * @param toCurrency   the ISO code of the target currency
     * @return the converted amount
     */
    BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency);
    Forex createForex(Forex forex);
    Forex getForexById(Long id);
    List<Forex> getAllForex();
    void updateForexRates(Map<String, BigDecimal> rates);

    Forex updateForex(Long id, Forex updatedForex);

    void deleteForex(Long id);
}
