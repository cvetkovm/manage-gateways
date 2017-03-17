package org.cvetkov.martin.controller;

import java.net.URI;
import java.util.List;

import org.cvetkov.martin.model.Gateway;
import org.cvetkov.martin.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(value = "/gateways/{serialNumber}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public String deleteGateway(@PathVariable("serialNumber") String serialNumber) {

		service.deleteGateway(serialNumber);
		return "Gateway with serial number " + serialNumber + " was deleted";
	}
	



}