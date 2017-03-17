package org.cvetkov.martin.error;

public class DeviceStatusNotValidException extends RuntimeException {
	
	private static final long serialVersionUID = 7L;
	private int uid;
	private String status;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DeviceStatusNotValidException(int uid, String status) {
		this.uid = uid;
		this.status = status;
	}
	
	
}
