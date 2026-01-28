package io.github.mateusluiz.finances.infra.persistence.entity;


import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    public UUID id;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String type;

    @Column(name = "initial_balance", nullable = false, precision = 19, scale = 2)
    public BigDecimal initialBalance;
}