package com.example.bank.repository;

import com.example.bank.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    @Query("SELECT ca FROM CustomerAccount ca WHERE ca.account.accountId = :accountId")
    List<CustomerAccount> findByAccountId(@Param("accountId") Long accountId);
}
