package io.github.mateusluiz.finances.domain.ports;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.github.mateusluiz.finances.domain.model.Transaction;

public interface TransactionRepository {
    void save(Transaction tx);
    List<Transaction> listByUserAndPeriod(UUID userId, LocalDate start, LocalDate end);
}