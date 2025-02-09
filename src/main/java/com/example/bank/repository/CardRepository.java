package com.example.bank.repository;

import com.example.bank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber AND c.password = :password")
    Optional<Card> findByCardNumber(@Param("cardNumber") String cardNumber, @Param("password") String password);

}
