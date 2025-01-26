package com.example.bank.service;

import com.example.bank.entity.Forex;
import com.example.bank.repository.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForexServiceImpl implements ForexService {

    private final ForexRepository forexRepository;

    @Autowired
    public ForexServiceImpl(ForexRepository forexRepository) {
        this.forexRepository = forexRepository;
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
    public void deleteForex(Long id) {
        forexRepository.deleteById(id);
    }
}
