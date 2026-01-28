package io.github.mateusluiz.finances.infra.persistence.repository;

import io.github.mateusluiz.finances.domain.model.Transaction;
import io.github.mateusluiz.finances.domain.ports.TransactionRepository;
import io.github.mateusluiz.finances.infra.persistence.entity.TransactionEntity;
import io.github.mateusluiz.finances.infra.persistence.mapper.TransactionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransactionRepositoryJpa implements TransactionRepository {

    private final EntityManager em;

    public TransactionRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Transaction tx) {
        em.persist(TransactionMapper.toEntity(tx));
    }

    @Override
    public List<Transaction> listByUserAndPeriod(UUID userId, LocalDate start, LocalDate end) {
        var rows = em.createQuery(
                "select t from TransactionEntity t " +
                        "where t.userId = :userId and t.date >= :start and t.date <= :end " +
                        "order by t.date desc, t.id desc",
                TransactionEntity.class
        ).setParameter("userId", userId)
         .setParameter("start", start)
         .setParameter("end", end)
         .getResultList();

        return rows.stream().map(TransactionMapper::fromEntity).toList();
    }
}