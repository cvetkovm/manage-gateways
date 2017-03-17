package org.cvetkov.martin.error;

public class GatewayAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	public GatewayAlreadyExistsException(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
}
