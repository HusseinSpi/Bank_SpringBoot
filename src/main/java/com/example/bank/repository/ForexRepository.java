package com.example.bank.repository;

import com.example.bank.entity.Forex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexRepository extends JpaRepository<Forex, Long> {
}
