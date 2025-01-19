package com.example.bank.service;

import com.example.bank.entity.Forex;
import com.example.bank.repository.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Forex updateForex(Forex forex) {
        return forexRepository.save(forex);
    }

    @Override
    public void deleteForex(Long id) {
        forexRepository.deleteById(id);
    }
}
