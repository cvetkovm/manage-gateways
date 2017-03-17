package org.cvetkov.martin.utilities;

import java.util.List;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.cvetkov.martin.model.Device;
import org.springframework.util.StringUtils;

public class Validator {

	public static boolean isValidStatus(String status) {
		if(StringUtils.isEmpty(status)) {
			return false;
		} else if(status.equals("online") || status.equals("offline")){
			return true;
		}else return false;
	}
	
	public static boolean noDuplicates(List<Device> devices) {
		int len = devices.size();
		for(int i = 0; i < len - 1; i++) {
			for(int j =i + 1; j < len; j++) {
				Device device1 = devices.get(i);
				Device device2 = devices.get(j);
				if(device1.getUid() == device2.getUid()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean validIp4Address(String ipAddress) {
		return InetAddressValidator.getInstance().isValidInet4Address(ipAddress);
	}
}
