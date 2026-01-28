package io.github.mateusluiz.finances.app.usecase;


import io.github.mateusluiz.finances.app.dto.TransactionCreateCommand;
import io.github.mateusluiz.finances.app.dto.TransactionResult;
import io.github.mateusluiz.finances.domain.model.Transaction;
import io.github.mateusluiz.finances.domain.ports.AccountRepository;
import io.github.mateusluiz.finances.domain.ports.CategoryRepository;
import io.github.mateusluiz.finances.domain.ports.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public CreateTransactionUseCase(TransactionRepository transactionRepository,
                                    AccountRepository accountRepository,
                                    CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionResult execute(UUID userId, TransactionCreateCommand cmd) {

        if (!accountRepository.existsByIdAndUserId(cmd.accountId(), userId)) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        var categoryType = categoryRepository.findTypeByIdAndUserId(cmd.categoryId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        if (!categoryType.equals(cmd.type().name())) {
            throw new IllegalArgumentException("Categoria incompatível com o tipo da transação");
        }

        Transaction tx = Transaction.create(
                userId,
                cmd.accountId(),
                cmd.categoryId(),
                cmd.type(),
                cmd.amount(),
                cmd.date(),
                cmd.description()
        );

        transactionRepository.save(tx);
        return TransactionResult.from(tx);
    }
}