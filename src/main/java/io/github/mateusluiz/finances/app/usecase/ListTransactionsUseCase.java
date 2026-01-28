package io.github.mateusluiz.finances.app.usecase;


import io.github.mateusluiz.finances.app.dto.TransactionResult;
import io.github.mateusluiz.finances.domain.ports.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ListTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public ListTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResult> execute(UUID userId, YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        return transactionRepository.listByUserAndPeriod(userId, start, end)
                .stream()
                .map(TransactionResult::from)
                .toList();
    }
}