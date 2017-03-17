package org.cvetkov.martin.error;

public class GatewayNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 2L;
	private String serialNumber;
	public GatewayNotFoundException(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
}
