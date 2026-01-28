package io.github.mateusluiz.finances.infra.persistence.repository;


import io.github.mateusluiz.finances.domain.ports.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryRepositoryJpa implements CategoryRepository {

    private final EntityManager em;

    public CategoryRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<String> findTypeByIdAndUserId(UUID id, UUID userId) {
        var list = em.createQuery(
                "select c.type from CategoryEntity c where c.id = :id and c.userId = :userId",
                String.class
        ).setParameter("id", id)
         .setParameter("userId", userId)
         .getResultList();

        return list.stream().findFirst();
    }
}
