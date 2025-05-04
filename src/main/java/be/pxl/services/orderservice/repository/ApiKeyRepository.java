package be.pxl.services.orderservice.repository;

import be.pxl.services.orderservice.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    ApiKey findFirstByOrderByIdAsc();

    Optional<ApiKey> findFirstByKeyValueContaining(String keyValue);
}
