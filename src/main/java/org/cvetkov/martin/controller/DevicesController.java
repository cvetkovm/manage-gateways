package org.cvetkov.martin.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cvetkov.martin.error.DeviceAlreadyExistsException;
import org.cvetkov.martin.error.DeviceNotFoundException;
import org.cvetkov.martin.error.DeviceStatusNotValidException;
import org.cvetkov.martin.error.DevicesNotFoundException;
import org.cvetkov.martin.error.GatewayNotFoundException;
import org.cvetkov.martin.error.TooManyDevicesException;
import org.cvetkov.martin.model.Device;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DevicesController {

	GatewayService service;

	@Autowired
	public DevicesController(GatewayService service) {
		this.service = service;
	}

	@RequestMapping(value="/gateways/{gatewaySerialNumber}/devices", method=RequestMethod.GET, produces="application/json")
	public List<Device> getDevices(@PathVariable String gatewaySerialNumber) {

		List<Device> devices = service.getDevices(gatewaySerialNumber);
		return devices;
	}

	@RequestMapping(value="/gateways/{gatewaySerialNumber}/devices/{deviceUid}", method=RequestMethod.GET, produces="application/json")
	public Device getDevice(@PathVariable("gatewaySerialNumber") String gatewaySerialNumber,
			@PathVariable("deviceUid") int deviceUid) {

		Device device = service.getDevice(gatewaySerialNumber, deviceUid);
		return device;
	}

	@ExceptionHandler(DevicesNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String devicesNotFound(DevicesNotFoundException e) {
		String gatewaySerialNumber = e.getGatewaySerialNumber();
		return "There are no devices for gateway with serial number " + gatewaySerialNumber;
	}

	@RequestMapping(value="/gateways/{gatewaySerialNumber}/devices", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Device> addDevice(
			@PathVariable("gatewaySerialNumber") String gatewaySerialNumber,
			@RequestBody Device device,
			UriComponentsBuilder ucb) {

		service.addDevice(gatewaySerialNumber, device);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/gateways/")
				.path(gatewaySerialNumber)
				.path("/devices/")
				.path("" + device.getUid())
				.build()
				.toUri();
		headers.setLocation(locationUri);
		ResponseEntity<Device> responseEntity = new ResponseEntity<Device>(device, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value = "/gateways/{gatewaySerialNumber}/devices/{deviceUID}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public String deleteDevice(@PathVariable("gatewaySerialNumber") String gatewaySerialNumber, 
			@PathVariable("deviceUID") int deviceUID) {

		service.deleteDevice(gatewaySerialNumber, deviceUID);
		return "Device with uid " + deviceUID + " for gateway with serial number " + gatewaySerialNumber + " was deleted";
	}

	@ExceptionHandler(DeviceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String deviceNotFound(DeviceNotFoundException e) {
		String gatewaySerialNumber = e.getGatewaySerialNumber();
		int deviceUID = e.getDeviceUid();
		return "Device with uid " + deviceUID + " for gateway with serial number " + gatewaySerialNumber + " was not found";
	}

	@ExceptionHandler(DeviceAlreadyExistsException.class)
	public ResponseEntity<String> devcieAlreadyExists(DeviceAlreadyExistsException e, HttpServletRequest request) {
		String serialNumber = e.getSerialNumber();
		int uid = e.getUid();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		URI locationUri = ucb.path("/" + uid).build().toUri();
		headers.setLocation(locationUri);
		String errorMessage = "Device with uid " + uid + " is already assignes to gateway with serial number " + serialNumber;
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, headers, HttpStatus.CONFLICT);
		return responseEntity;
	}

	@ExceptionHandler(TooManyDevicesException.class)
	public ResponseEntity<String> tooManyDevices(TooManyDevicesException e) {
		String errorMessage = "Gateway can not have more than 10 devices";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
		return responseEntity;
	}

	@ExceptionHandler(DeviceStatusNotValidException.class)
	public ResponseEntity<String> statusNotValid(DeviceStatusNotValidException e) {
		String errorMessage = "The status [" + e.getStatus() + "] of the device with uid " + e.getUid() + " is not valid.";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
		return responseEntity;
	}
	
	@ExceptionHandler(GatewayNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String gatewayNotFound(GatewayNotFoundException e) {
		String serialNumber = e.getSerialNumber();
		return "Gateway with serial number " + serialNumber + " was not found";
	}

}