package org.cvetkov.martin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="devices")
public class Device {
	@Id
	private int uid;
	private String vendor;
	private Date dateCreated;
	private String status;
	private String gatewaySerialNumber;
	
	public Device(int uid, String vendor, Date dateCreated, String status, String gatewaySerialNumber) {
		super();
		this.uid = uid;
		this.vendor = vendor;
		this.dateCreated = dateCreated;
		this.status = status;
		this.gatewaySerialNumber = gatewaySerialNumber;
	}
	
	public Device() {
		// TODO Auto-generated constructor stub
	}

	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGatewaySerialNumber() {
		return gatewaySerialNumber;
	}
	
	public void setGatewaySerialNumber(String gatewaySerialNumber) {
		this.gatewaySerialNumber = gatewaySerialNumber;
	}

}
