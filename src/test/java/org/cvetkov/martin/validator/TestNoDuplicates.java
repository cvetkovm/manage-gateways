package org.cvetkov.martin.validator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cvetkov.martin.model.Device;
import org.cvetkov.martin.utilities.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestNoDuplicates {

	@Test
	public void testNoDuplicatesWhenEmptyList(){
		List<Device> devices = new ArrayList<Device>();
		assertTrue(Validator.noDuplicates(devices));
	}
	
	@Test
	public void testNoDuplicatesWhenOneDevice(){
		List<Device> devices = new ArrayList<Device>();
		devices.add(new Device());
		assertTrue(Validator.noDuplicates(devices));
	}
	
	@Test
	public void testNoDuplicatesWhenTwoDifferentDevices(){
		List<Device> devices = new ArrayList<Device>();
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(2,"HTC",new Date(), "online","VVV"));
		assertTrue(Validator.noDuplicates(devices));
	}
	
	@Test
	public void testNoDuplicatesWhenTwoSameDevices(){
		List<Device> devices = new ArrayList<Device>();
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		assertFalse(Validator.noDuplicates(devices));
	}
	
	@Test
	public void testNoDuplicatesWhenThreeDevicesTwoSame(){
		List<Device> devices = new ArrayList<Device>();
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(2,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		assertFalse(Validator.noDuplicates(devices));
	}
	
	@Test
	public void testNoDuplicatesWhenThreeDifferentDevices(){
		List<Device> devices = new ArrayList<Device>();
		devices.add(new Device(1,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(2,"HTC",new Date(), "online","AAA"));
		devices.add(new Device(3,"HTC",new Date(), "online","AAA"));
		assertTrue(Validator.noDuplicates(devices));
	}

	
}
