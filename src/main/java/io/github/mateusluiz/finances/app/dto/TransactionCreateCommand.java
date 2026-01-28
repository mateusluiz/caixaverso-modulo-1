package io.github.mateusluiz.finances.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.github.mateusluiz.finances.domain.model.TransactionType;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionCreateCommand(
        @NotNull UUID accountId,
        @NotNull UUID categoryId,
        @NotNull TransactionType type,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate date,
        String description
) {}