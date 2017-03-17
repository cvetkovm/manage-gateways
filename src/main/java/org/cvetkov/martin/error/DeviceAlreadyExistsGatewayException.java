package org.cvetkov.martin.error;

public class DeviceAlreadyExistsGatewayException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private int uid;
	public DeviceAlreadyExistsGatewayException(String serialNumber, int uid) {
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
