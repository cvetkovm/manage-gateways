package org.cvetkov.martin.service;

import java.util.List;

import org.cvetkov.martin.error.DeviceAlreadyExistsException;
import org.cvetkov.martin.error.DeviceNotFoundException;
import org.cvetkov.martin.error.DeviceStatusNotValidException;
import org.cvetkov.martin.error.DevicesNotFoundException;
import org.cvetkov.martin.error.GatewayAlreadyExistsException;
import org.cvetkov.martin.error.GatewayNotFoundException;
import org.cvetkov.martin.error.InvalidRequestException;
import org.cvetkov.martin.error.IpAddressNotValidException;
import org.cvetkov.martin.error.TooManyDevicesException;
import org.cvetkov.martin.model.Device;
import org.cvetkov.martin.model.Gateway;
import org.cvetkov.martin.repository.DeviceJpaRepository;
import org.cvetkov.martin.repository.GatewayJpaRepository;
import org.cvetkov.martin.utilities.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GatewayServiceImpl implements GatewayService{
	
	GatewayJpaRepository gatewayJpaRepository;
	DeviceJpaRepository deviceJpaRepository;

	@Autowired
	public GatewayServiceImpl( GatewayJpaRepository gatewayJpaRepository,
			DeviceJpaRepository deviceJpaRepository) {
		this.gatewayJpaRepository = gatewayJpaRepository;
		this.deviceJpaRepository = deviceJpaRepository;
	}

	public List<Gateway> getAllGateways() {
		List<Gateway> allGateways = gatewayJpaRepository.findAll();
		return allGateways;
	}

	public Gateway getGateway(String gatewaySerialNumber) {
		Gateway gateway = gatewayJpaRepository.findBySerialNumber(gatewaySerialNumber);
		if (gateway == null) {
			throw new GatewayNotFoundException(gatewaySerialNumber);
		}
		return gateway;
	}
	
	public void addGateway(Gateway gateway) {
		Gateway existingGateway = gatewayJpaRepository.findBySerialNumber(gateway.getSerialNumber());
		if (existingGateway != null) {
			throw new GatewayAlreadyExistsException(gateway.getSerialNumber());
		}
		if(gateway.getDevices().size() > 10) {
			throw new TooManyDevicesException(gateway.getSerialNumber());
		}
		
		if(!Validator.validIp4Address(gateway.getIpAddress())) {
			throw new IpAddressNotValidException(gateway.getSerialNumber(), gateway.getIpAddress());
		}
		
		for (Device device : gateway.getDevices()) {
			if(!Validator.isValidStatus(device.getStatus())) {
				throw new DeviceStatusNotValidException(device.getUid(), device.getStatus());
			}
		}
		
		if(!Validator.noDuplicates(gateway.getDevices())){
			throw new InvalidRequestException();
		}

		for (Device device : gateway.getDevices()) {
			device.setGatewaySerialNumber(gateway.getSerialNumber());
			String serialNumber = deviceExists(device);
			if(!serialNumber.equals("")) {
				throw new DeviceAlreadyExistsException(serialNumber, device.getUid());
			}
			
		}
		gatewayJpaRepository.save(gateway);
		deviceJpaRepository.save(gateway.getDevices());
	}
	
	public void deleteGateway(String serialNumber) {
		Gateway gateway = gatewayJpaRepository.findBySerialNumber(serialNumber);
		if (gateway == null) {
			throw new GatewayNotFoundException(serialNumber);
		}

		for (Device device : gateway.getDevices()) {
			deviceJpaRepository.delete(device);
		}
		gatewayJpaRepository.delete(gateway);
	}
	
	public List<Device> getDevices(String gatewaySerialNumber) {
		List<Device> devices = deviceJpaRepository.findByGatewaySerialNumber(gatewaySerialNumber);
		if (devices == null || devices.isEmpty()) {
			throw new DevicesNotFoundException(gatewaySerialNumber);
		}
		return devices;
	}
	
	public Device getDevice(String gatewaySerialNumber, int deviceUid) {
		Device device = deviceJpaRepository.findByGatewaySerialNumberAndUid(gatewaySerialNumber, deviceUid);
		if (device == null) {
			throw new DeviceNotFoundException(gatewaySerialNumber, deviceUid);
		}
		return device;
	}
	
	public void addDevice(String gatewaySerialNumber, Device device) {
		Gateway gateway = gatewayJpaRepository.findBySerialNumber(gatewaySerialNumber);
		if (gateway == null) {
			throw new GatewayNotFoundException(gatewaySerialNumber);
		}
		Device existingDevice = deviceJpaRepository.findByUid(device.getUid());
		if (existingDevice != null) {
			throw new DeviceAlreadyExistsException(existingDevice.getGatewaySerialNumber(), existingDevice.getUid());
		}

		int numberOfDevices = deviceJpaRepository.findByGatewaySerialNumber(gatewaySerialNumber).size();
		if(numberOfDevices == MAX_NUMBER_OF_DEVICES) {
			throw new TooManyDevicesException(gatewaySerialNumber);
		}

		if(!Validator.isValidStatus(device.getStatus())) {
			throw new DeviceStatusNotValidException(device.getUid(), device.getStatus());
		}

		device.setGatewaySerialNumber(gatewaySerialNumber);
		deviceJpaRepository.save(device);
	}
	
	public String deviceExists(Device device) {
		Device existingDevice = deviceJpaRepository.findByUid(device.getUid());
		if (existingDevice != null) {
			return existingDevice.getGatewaySerialNumber();
		} else {
			return "";
		}
	}
	
	public void deleteDevice(String gatewaySerialNumber,  int deviceUID) {
		Gateway gateway = gatewayJpaRepository.findBySerialNumber(gatewaySerialNumber);
		if (gateway == null) {
			throw new GatewayNotFoundException(gatewaySerialNumber);
		}
		Device device = deviceJpaRepository.findByGatewaySerialNumberAndUid(gatewaySerialNumber, deviceUID);
		if (device == null) {
			throw new DeviceNotFoundException(gatewaySerialNumber, deviceUID);
		}
		deviceJpaRepository.delete(device);
	}
}
