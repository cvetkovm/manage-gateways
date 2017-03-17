package org.cvetkov.martin.error;

public class TooManyDevicesException extends RuntimeException {
	
	private static final long serialVersionUID = 6L;
	private String serialNumber;
	public TooManyDevicesException(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
}
