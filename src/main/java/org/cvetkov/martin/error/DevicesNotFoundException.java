package org.cvetkov.martin.error;

public class DevicesNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 3L;
	private String gatewaySerialNumber;
	public DevicesNotFoundException(String gatewaySerialNumber) {
		this.gatewaySerialNumber = gatewaySerialNumber;
	}
	public String getGatewaySerialNumber() {
		return gatewaySerialNumber;
	}
}
