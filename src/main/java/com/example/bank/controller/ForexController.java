package com.example.bank.controller;

import com.example.bank.entity.Forex;
import com.example.bank.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Forex (أسعار الصرف).
 */
@RestController
@RequestMapping("/api/forex")
public class ForexController {

    private final ForexService forexService;

    @Autowired
    public ForexController(ForexService forexService) {
        this.forexService = forexService;
    }

    /**
     * إنشاء Forex جديد.
     */
    @PostMapping
    public Forex createForex(@RequestBody Forex forex) {
        return forexService.createForex(forex);
    }

    /**
     * الحصول على Forex عبر معرّفه (ID).
     */
    @GetMapping("/{id}")
    public Forex getForex(@PathVariable Long id) {
        return forexService.getForexById(id);
    }

    /**
     * الحصول على جميع الـ Forex.
     */
    @GetMapping
    public List<Forex> getAllForex() {
        return forexService.getAllForex();
    }

    /**
     * تعديل Forex موجود عبر ID.
     */
    @PutMapping("/{id}")
    public Forex updateForex(@PathVariable Long id, @RequestBody Forex updatedForex) {
        // لا نستطيع استخدام setForexId(id). بدلاً من ذلك نمرر المعرف للـ Service.
        return forexService.updateForex(id, updatedForex);
    }

    /**
     * حذف Forex عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteForex(@PathVariable Long id) {
        forexService.deleteForex(id);
    }
}
