package org.cvetkov.martin.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="gateways")
public class Gateway {
	@Id
	private String serialNumber;
	private String name;
	private String ipAddress;
	
	@OneToMany(mappedBy="gatewaySerialNumber")
	@NotFound(action=NotFoundAction.IGNORE)
    private List<Device> devices;
	public Gateway() {}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Gateway(String serialNumber, String name, String ipAddress) {
		super();
		this.serialNumber = serialNumber;
		this.name = name;
		this.ipAddress = ipAddress;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
