package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Card;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Card createCard(Card card) {
        Account account = accountRepository.findById(card.getAccount().getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + card.getAccount().getAccountId()));

        Card newCard = new Card();
        newCard.setAccount(account);
        newCard.setCardNumber(card.getCardNumber());
        newCard.setCardType(card.getCardType());
        newCard.setCardStatus(card.getCardStatus());
        newCard.setPassword(card.getPassword());
        newCard.setCcv(card.getCcv());
        newCard.setName(card.getName());
        newCard.setExpirationDate(card.getExpirationDate());
        return cardRepository.save(newCard);
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
