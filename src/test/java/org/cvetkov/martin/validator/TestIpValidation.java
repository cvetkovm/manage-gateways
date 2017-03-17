package org.cvetkov.martin.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cvetkov.martin.utilities.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestIpValidation{

	@Test
	public void testIpValidationWhenIpIsNull(){
		String ipAddress = null;
		assertFalse(Validator.validIp4Address(ipAddress));
	}
	
	@Test
	public void testIpValidationWhenIpIsEmptyString(){
		String ipAddress = "";
		assertFalse(Validator.validIp4Address(ipAddress));
	}
	
	@Test
	public void testIpValidationWhenTheFormatOfTheIpIsIncorrect(){
		String ipAddress = "bbb";
		assertFalse(Validator.validIp4Address(ipAddress));
	}
	
	@Test
	public void testIpValidationWhenTheFormatOfTheIpIsCorrectButTheValuesAreNot(){
		String ipAddress = "999.999.999.999";
		assertFalse(Validator.validIp4Address(ipAddress));
	}
	
	@Test
	public void testIpValidationWhenIpIsCorrect(){
		String ipAddress = "127.0.0.1";
		assertTrue(Validator.validIp4Address(ipAddress));
	}
	
}
