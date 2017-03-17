package org.cvetkov.martin.error;

public class DeviceAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private int uid;
	public DeviceAlreadyExistsException(String serialNumber, int uid) {
		this.serialNumber = serialNumber;
		this.uid = uid;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public int getUid() {
		return uid;
	}
}
