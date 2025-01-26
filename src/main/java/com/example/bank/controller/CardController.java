package com.example.bank.controller;

import com.example.bank.entity.Card;
import com.example.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Card (البطاقات).
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * إنشاء بطاقة جديدة.
     */
    @PostMapping
    public Card createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    /**
     * الحصول على بطاقة عبر معرّفها (ID).
     */
    @GetMapping("/{id}")
    public Card getCard(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    /**
     * الحصول على جميع البطاقات.
     */
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    /**
     * تعديل بطاقة موجودة عبر ID.
     */
    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        // لا نستطيع استخدام setCardId(id). بدلاً من ذلك نمرر المعرف للـ Service.
        return cardService.updateCard(id, updatedCard);
    }

    /**
     * حذف بطاقة عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
