package org.cvetkov.martin.error;

public class DeviceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 3L;
	private String gatewaySerialNumber;
	private int uid;
	public DeviceNotFoundException(String gatewaySerialNumber, int uid) {
		this.gatewaySerialNumber = gatewaySerialNumber;
		this.uid = uid;
	}
	public String getGatewaySerialNumber() {
		return gatewaySerialNumber;
	}
	
	public int getDeviceUid() {
		return uid;
	}
}
