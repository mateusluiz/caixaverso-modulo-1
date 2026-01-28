package io.github.mateusluiz.finances.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.github.mateusluiz.finances.domain.model.Transaction;
import io.github.mateusluiz.finances.domain.model.TransactionType;

public record TransactionResult(
        UUID id,
        UUID accountId,
        UUID categoryId,
        TransactionType type,
        BigDecimal amount,
        LocalDate date,
        String description
) {
    public static TransactionResult from(Transaction tx) {
        return new TransactionResult(
                tx.getId(),
                tx.getAccountId(),
                tx.getCategoryId(),
                tx.getType(),
                tx.getAmount(),
                tx.getDate(),
                tx.getDescription()
        );
    }
}