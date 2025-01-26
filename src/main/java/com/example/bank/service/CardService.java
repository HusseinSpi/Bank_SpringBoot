package com.example.bank.service;

import com.example.bank.entity.Card;
import java.util.List;

public interface CardService {
    Card createCard(Card card);
    Card getCardById(Long id);
    List<Card> getAllCards();

    Card updateCard(Long id, Card updatedCard);

    void deleteCard(Long id);
}
