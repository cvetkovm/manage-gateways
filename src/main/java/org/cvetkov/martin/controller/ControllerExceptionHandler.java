package org.cvetkov.martin.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.cvetkov.martin.error.DeviceAlreadyExistsException;
import org.cvetkov.martin.error.DeviceAlreadyExistsGatewayException;
import org.cvetkov.martin.error.DeviceNotFoundException;
import org.cvetkov.martin.error.DeviceStatusNotValidException;
import org.cvetkov.martin.error.DevicesNotFoundException;
import org.cvetkov.martin.error.ErrorMessage;
import org.cvetkov.martin.error.GatewayAlreadyExistsException;
import org.cvetkov.martin.error.GatewayNotFoundException;
import org.cvetkov.martin.error.InvalidRequestException;
import org.cvetkov.martin.error.IpAddressNotValidException;
import org.cvetkov.martin.error.TooManyDevicesException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice()
@RestController 
public class ControllerExceptionHandler {
	
	@ExceptionHandler(DeviceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage deviceNotFound(DeviceNotFoundException e) {
		String gatewaySerialNumber = e.getGatewaySerialNumber();
		int deviceUID = e.getDeviceUid();
		return new ErrorMessage("Device with uid " + deviceUID + " for gateway with serial number " + gatewaySerialNumber + " was not found");
	}


	@ExceptionHandler(TooManyDevicesException.class)
	public ResponseEntity<ErrorMessage> tooManyDevices(TooManyDevicesException e) {
		String errorMessage = "Gateway can not have more than 10 devices";
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), new HttpHeaders(), HttpStatus.FORBIDDEN);
		return responseEntity;
	}

	
	@ExceptionHandler(DeviceStatusNotValidException.class)
	public ResponseEntity<ErrorMessage> statusNotValid(DeviceStatusNotValidException e) {
		String errorMessage = "The status [" + e.getStatus() + "] of the device with uid " + e.getUid() + " is not valid.";
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), new HttpHeaders(), HttpStatus.FORBIDDEN);
		return responseEntity;
	}

	
	@ExceptionHandler(GatewayNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage gatewayNotFound(GatewayNotFoundException e) {
		String serialNumber = e.getSerialNumber();
		return new ErrorMessage("Gateway with serial number " + serialNumber + " was not found");
	}
	
	
	@ExceptionHandler(DeviceAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> deviceAlreadyExists(DeviceAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		int uid = e.getUid();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/" + uid).build().toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Device with uid " + uid + " is already assigned to gateway with serial number " + serialNumber;
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), headers, HttpStatus.CONFLICT);
		return responseEntity;
	}
	
	
	@ExceptionHandler(DevicesNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage devicesNotFound(DevicesNotFoundException e) {
		String gatewaySerialNumber = e.getGatewaySerialNumber();
		return new ErrorMessage("There are no devices for gateway with serial number " + gatewaySerialNumber);
	}
	
	@ExceptionHandler(GatewayAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> gatewayAlreadyExists(GatewayAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/"+String.valueOf(e.getSerialNumber()))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Gateway with serial number " + serialNumber + " already exists";
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), headers, HttpStatus.CONFLICT);
		return responseEntity;
	}
	
	@ExceptionHandler(IpAddressNotValidException.class)
	public ResponseEntity<ErrorMessage> notValidIpAddress(IpAddressNotValidException e) {
		String errorMessage = "The ip address " + e.getIpAddress() + " is not valid";
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorMessage> invalidRequest(InvalidRequestException e) {
		ErrorMessage errorMessage = new ErrorMessage("The sent data is invalid");
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	@ExceptionHandler(DeviceAlreadyExistsGatewayException.class)
	public ResponseEntity<ErrorMessage> deviceAlreadyExistsGateway(DeviceAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		int uid = e.getUid();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/" + serialNumber + "/devices/" + uid).build().toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Device with uid " + uid + " is already assigned to gateway with serial number: " + serialNumber;
		ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), headers, HttpStatus.CONFLICT);
		return responseEntity;
	}

}
