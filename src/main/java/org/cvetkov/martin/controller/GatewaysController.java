package org.cvetkov.martin.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cvetkov.martin.error.DeviceAlreadyExistsException;
import org.cvetkov.martin.error.DeviceStatusNotValidException;
import org.cvetkov.martin.error.GatewayAlreadyExistsException;
import org.cvetkov.martin.error.GatewayNotFoundException;
import org.cvetkov.martin.error.InvalidRequestException;
import org.cvetkov.martin.error.IpAddressNotValidException;
import org.cvetkov.martin.error.TooManyDevicesException;
import org.cvetkov.martin.model.Gateway;
import org.cvetkov.martin.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class GatewaysController {

	GatewayService service;

	@Autowired
	public GatewaysController(GatewayService service) {
		this.service = service;
	}

	@RequestMapping(value="/gateways", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Gateway> getAllGateways() {
		
		List<Gateway> allGateways = service.getAllGateways();
		return allGateways;
		
	}

	@RequestMapping(value="/gateways/{gatewaySerialNumber}", method=RequestMethod.GET, produces="application/json")
	public Gateway getGateway(@PathVariable String gatewaySerialNumber) {

		Gateway gateway = service.getGateway(gatewaySerialNumber);
		return gateway;
		
	}

	@ExceptionHandler(GatewayNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String gatewayNotFound(GatewayNotFoundException e) {
		String serialNumber = e.getSerialNumber();
		return "Gateway with serial number " + serialNumber + " was not found";
	}
	
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json",value="/gateways")
	public ResponseEntity<Gateway> addGateway(
			@RequestBody Gateway gateway,
			UriComponentsBuilder ucb) {
		
		service.addGateway(gateway);		
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/gateways/")
				.path(String.valueOf(gateway.getSerialNumber()))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		ResponseEntity<Gateway> responseEntity = new ResponseEntity<Gateway>(gateway, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@ExceptionHandler(GatewayAlreadyExistsException.class)
	public ResponseEntity<String> gatewayAlreadyExists(GatewayAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/"+String.valueOf(e.getSerialNumber()))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Gateway with serial number " + serialNumber + " already exists";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, headers, HttpStatus.CONFLICT);
		return responseEntity;
	}

	@RequestMapping(value = "/gateways/{serialNumber}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public String deleteGateway(@PathVariable("serialNumber") String serialNumber) {

		service.deleteGateway(serialNumber);
		return "Gateway with serial number " + serialNumber + " was deleted";
	}
	
	@ExceptionHandler(TooManyDevicesException.class)
	public ResponseEntity<String> tooManyDevices(TooManyDevicesException e) {
		String errorMessage = "Gateway can not have more than 10 devices";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
		return responseEntity;
	}
	
	@ExceptionHandler(IpAddressNotValidException.class)
	public ResponseEntity<String> notValidIpAddress(IpAddressNotValidException e) {
		String errorMessage = "The ip address " + e.getIpAddress() + " is not valid";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	@ExceptionHandler(DeviceStatusNotValidException.class)
	public ResponseEntity<String> statusNotValid(DeviceStatusNotValidException e) {
		String errorMessage = "The status [" + e.getStatus() + "] of the device with uid " + e.getUid() + " is not valid.";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<String> invalidRequest(InvalidRequestException e) {
		String errorMessage = "The request is invalid";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	@ExceptionHandler(DeviceAlreadyExistsException.class)
	public ResponseEntity<String> devcieAlreadyExists(DeviceAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		int uid = e.getUid();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/" + serialNumber + "/devices/" + uid).build().toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Device with uid " + uid + " is already assignes to gateway with serial number " + serialNumber;
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, headers, HttpStatus.CONFLICT);
		return responseEntity;
	}


}