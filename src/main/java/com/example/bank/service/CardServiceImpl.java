package com.example.bank.service;

import com.example.bank.entity.Card;
import com.example.bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card updateCard(Long id, Card updatedCard) {
        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        existingCard.setName(updatedCard.getName());
        existingCard.setCardNumber(updatedCard.getCardNumber());
        existingCard.setExpirationDate(updatedCard.getExpirationDate());
        existingCard.setCcv(updatedCard.getCcv());
        existingCard.setCardStatus(updatedCard.getCardStatus());
        existingCard.setCardType(updatedCard.getCardType());
        existingCard.setAccount(updatedCard.getAccount());
        existingCard.setUpdatedAt(LocalDateTime.now());

        return cardRepository.save(existingCard);
    }

    @Override
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
