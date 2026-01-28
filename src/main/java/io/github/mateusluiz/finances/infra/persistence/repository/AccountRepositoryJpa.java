package io.github.mateusluiz.finances.infra.persistence.repository;

import io.github.mateusluiz.finances.domain.ports.AccountRepository;
import io.github.mateusluiz.finances.infra.persistence.entity.AccountEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@ApplicationScoped
public class AccountRepositoryJpa implements AccountRepository {

    private final EntityManager em;

    public AccountRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean existsByIdAndUserId(UUID id, UUID userId) {
        Long count = em.createQuery(
                "select count(a) from AccountEntity a where a.id = :id and a.userId = :userId",
                Long.class
        ).setParameter("id", id)
         .setParameter("userId", userId)
         .getSingleResult();

        return count != null && count > 0;
    }
}
