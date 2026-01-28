package io.github.mateusluiz.finances.domain.ports;

import java.util.UUID;

public interface AccountRepository {
    boolean existsByIdAndUserId(UUID id, UUID userId);
}