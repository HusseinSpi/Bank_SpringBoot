package com.example.bank.service;

import com.example.bank.entity.Card;

import java.util.List;

public interface CardService {
    Card createCard(Card card);
    Card getCardById(Long id);
    List<Card> getAllCards();
    Card updateCard(Card card);
    void deleteCard(Long id);
}
