package io.github.mateusluiz.finances.infra.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    public UUID id;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @Column(name = "account_id", nullable = false)
    public UUID accountId;

    @Column(name = "category_id", nullable = false)
    public UUID categoryId;

    @Column(nullable = false)
    public String type;

    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal amount;

    @Column(nullable = false)
    public LocalDate date;

    public String description;
}
