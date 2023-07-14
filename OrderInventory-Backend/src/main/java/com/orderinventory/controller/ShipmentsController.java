package com.orderinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.CustomDto;
import com.orderinventory.dto.ShipmentsDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Stores;
import com.orderinventory.services.ShipmentsServices;

@RestController
@RequestMapping("/api/shipment")
@CrossOrigin(origins = "http://localhost:4200")
public class ShipmentsController {
	@Autowired
	private ShipmentsServices shipmentService;

	//Save Shipments
	@PostMapping("/add")
	public ResponseEntity<String> saveShipments(@RequestBody ShipmentsDto shipmentsDto) {
		String saveShipments = shipmentService.saveShipments(shipmentsDto);
		return new ResponseEntity<String>(saveShipments, HttpStatus.OK);
	}
	
	//Get Shipments for matching customerId
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<List<ShipmentsDto>> getShipmentByCustomer(@PathVariable Integer customerId){
		Customers customer = new Customers(customerId);
		List<ShipmentsDto> shipmentsByCustomerId = shipmentService.getShipmentsByCustomerId(customer);
		return new ResponseEntity<List<ShipmentsDto>>(shipmentsByCustomerId,HttpStatus.OK);
	}

	//Get Top ten most shipped customers
	@GetMapping("/toptencustomers")
	public ResponseEntity<List<CustomDto>> getTopTenMostShippedCustomers(){
		List<CustomDto> customersDtos= shipmentService.getTopTenMostShippedCustomers();
		System.out.println("-----In Controller list of custom dto"+customersDtos.get(1).getFullName());
		return new ResponseEntity<List<CustomDto>>(customersDtos,HttpStatus.OK);
		
	}
	
	//Get Shipment By storeId
	@GetMapping("/shipment/stores/{storeId}")
	public ResponseEntity<List<ShipmentsDto>> getShipmentsByStoreId(@PathVariable Integer storeId){
		Stores store=new Stores(storeId);
		
		List<ShipmentsDto> shipmentsByStoreId = shipmentService.getShipmentsByStoreId(store);
		return new ResponseEntity<List<ShipmentsDto>>(shipmentsByStoreId,HttpStatus.FOUND);
		
	}
}
