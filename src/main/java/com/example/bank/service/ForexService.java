package com.example.bank.service;

import com.example.bank.entity.Forex;
import java.util.List;

public interface ForexService {
    Forex createForex(Forex forex);
    Forex getForexById(Long id);
    List<Forex> getAllForex();

    Forex updateForex(Long id, Forex updatedForex);

    void deleteForex(Long id);
}
