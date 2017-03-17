package org.cvetkov.martin.repository;

import java.util.List;

import org.cvetkov.martin.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceJpaRepository extends JpaRepository<Device, Long> {
	List<Device> findByGatewaySerialNumber(String gatewaySerialNumber);
	Device findByGatewaySerialNumberAndUid(String gatewaySerialNumber, int deviceUid);
	Device findByUid(int uid);
}
