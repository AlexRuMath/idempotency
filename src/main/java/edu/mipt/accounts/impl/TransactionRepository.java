package edu.mipt.accounts.impl;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Transactions> findById(String rqUid);
}
