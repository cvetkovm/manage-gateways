package org.cvetkov.martin.repository;

import org.cvetkov.martin.model.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayJpaRepository extends JpaRepository<Gateway, Long> {
	Gateway findBySerialNumber(String gatewaySerialNumber);
}
