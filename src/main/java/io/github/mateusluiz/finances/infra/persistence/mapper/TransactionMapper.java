package io.github.mateusluiz.finances.infra.persistence.mapper;

import io.github.mateusluiz.finances.domain.model.Transaction;
import io.github.mateusluiz.finances.domain.model.TransactionType;
import io.github.mateusluiz.finances.infra.persistence.entity.TransactionEntity;

public final class TransactionMapper {
    private TransactionMapper() {}

    public static TransactionEntity toEntity(Transaction tx) {
        var e = new TransactionEntity();
        e.id = tx.getId();
        e.userId = tx.getUserId();
        e.accountId = tx.getAccountId();
        e.categoryId = tx.getCategoryId();
        e.type = tx.getType().name();
        e.amount = tx.getAmount();
        e.date = tx.getDate();
        e.description = tx.getDescription();
        return e;
    }

    public static Transaction fromEntity(TransactionEntity e) {
        return Transaction.rehydrate(
                e.id,
                e.userId,
                e.accountId,
                e.categoryId,
                TransactionType.valueOf(e.type),
                e.amount,
                e.date,
                e.description
        );
    }
}