package com.example.bank.controller;

import com.example.bank.entity.Forex;
import com.example.bank.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forex")
public class ForexController {

    private final ForexService forexService;

    @Autowired
    public ForexController(ForexService forexService) {
        this.forexService = forexService;
    }

    @PostMapping
    public Forex createForex(@RequestBody Forex forex) {
        return forexService.createForex(forex);
    }

    @GetMapping("/{id}")
    public Forex getForex(@PathVariable Long id) {
        return forexService.getForexById(id);
    }

    @GetMapping
    public List<Forex> getAllForex() {
        return forexService.getAllForex();
    }

    @PutMapping("/{id}")
    public Forex updateForex(@PathVariable Long id, @RequestBody Forex forex) {
        forex.setForexId(id);
        return forexService.updateForex(forex);
    }

    @DeleteMapping("/{id}")
    public void deleteForex(@PathVariable Long id) {
        forexService.deleteForex(id);
    }
}
