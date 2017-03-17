package org.cvetkov.martin.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cvetkov.martin.utilities.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestStatusValidation{

	@Test
	public void testStatusValidationWhenStatusIsNull(){
		String status = null;
		assertFalse(Validator.isValidStatus(status));
	}
	
	@Test
	public void testStatusValidationWhenStatusIsEmptyString(){
		String status = "";
		assertFalse(Validator.isValidStatus(status));
	}
	
	@Test
	public void testStatusValidationWhenTheStatusIsNotCorrect(){
		String status = "bbb";
		assertFalse(Validator.isValidStatus(status));
	}
	
	@Test
	public void testStatusValidationWhenTheStatusIsOnline(){
		String status = "online";
		assertTrue(Validator.isValidStatus(status));
	}
	
	@Test
	public void testStatusValidationWhenTheStatusIsOffline(){
		String status = "offline";
		assertTrue(Validator.isValidStatus(status));
	}
	
}
