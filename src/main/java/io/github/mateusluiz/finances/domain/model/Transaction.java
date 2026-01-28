package io.github.mateusluiz.finances.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final UUID userId;
    private final UUID accountId;
    private final UUID categoryId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;

    private Transaction(UUID id, UUID userId, UUID accountId, UUID categoryId,
                        TransactionType type, BigDecimal amount, LocalDate date, String description) {
        this.id = id;
        this.userId = userId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public static Transaction create(UUID userId, UUID accountId, UUID categoryId,
                                     TransactionType type, BigDecimal amount,
                                     LocalDate date, String description) {
        validate(amount, date);
        return new Transaction(UUID.randomUUID(), userId, accountId, categoryId, type, amount, date, description);
    }

    public static Transaction rehydrate(UUID id, UUID userId, UUID accountId, UUID categoryId,
                                        TransactionType type, BigDecimal amount,
                                        LocalDate date, String description) {
        if (id == null) throw new IllegalArgumentException("id é obrigatório");
        validate(amount, date);
        return new Transaction(id, userId, accountId, categoryId, type, amount, date, description);
    }

    private static void validate(BigDecimal amount, LocalDate date) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount deve ser > 0");
        if (date == null) throw new IllegalArgumentException("date é obrigatório");
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getAccountId() { return accountId; }
    public UUID getCategoryId() { return categoryId; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
}