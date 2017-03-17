package org.cvetkov.martin.controller;

import java.net.URI;
import java.util.List;

import org.cvetkov.martin.model.Device;
import org.cvetkov.martin.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}