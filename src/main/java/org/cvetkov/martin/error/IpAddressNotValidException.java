package org.cvetkov.martin.error;

public class IpAddressNotValidException extends RuntimeException {
	
	private static final long serialVersionUID = 7L;
	private String serialNumber;
	private String ipAddress;
	public IpAddressNotValidException(String serialNumber, String ipAddress) {
		this.serialNumber = serialNumber;
		this.ipAddress = ipAddress;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
}
