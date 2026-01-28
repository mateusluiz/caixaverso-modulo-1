package io.github.mateusluiz.finances.domain.ports;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Optional<String> findTypeByIdAndUserId(UUID id, UUID userId);
}
