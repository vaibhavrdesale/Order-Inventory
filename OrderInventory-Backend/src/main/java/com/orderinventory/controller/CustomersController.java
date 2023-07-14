package com.orderinventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.CountDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.ResponseDto;
import com.orderinventory.dto.ShipmentCustomDto;
import com.orderinventory.services.CustomersServices;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomersController {

	@Autowired
	private CustomersServices customerServices;
	//Get Customers By fullName
	@GetMapping("/customers/name/{fullName}")
	public ResponseEntity<List<CustomersDto>> getCustomersByFullName(@PathVariable String fullName) {
		List<CustomersDto> customerDtos = customerServices.getCustomersByFullName(fullName);
		return new ResponseEntity<List<CustomersDto>>(customerDtos, HttpStatus.OK);
	}

	//Get Customers By EmailAddress
	@GetMapping("/customers/email-{emailAddress}")
	public ResponseEntity<List<CustomersDto>> getCustomersByEmail(@PathVariable String emailAddress) {
		List<CustomersDto> cusDto = customerServices.getCustomersByEmail(emailAddress);

		return new ResponseEntity<List<CustomersDto>> (cusDto, HttpStatus.OK);
	}

	//Save Customer
	@PostMapping("/customers")
	public ResponseEntity<ResponseDto> saveCustomers(@Valid @RequestBody CustomersDto customersDto) {
		ResponseDto saveCustomers = customerServices.saveCustomers(customersDto);
		return new ResponseEntity<ResponseDto>(saveCustomers, HttpStatus.OK);

	}
	
	//Update Customer
	@PutMapping("/customers")
	public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomersDto customersDto){
		    ResponseDto updateCustomer = customerServices.updateCustomer(customersDto);
		    
		return new ResponseEntity<ResponseDto>(updateCustomer,HttpStatus.OK);
		
	}
	//Get All Customers
	@GetMapping("/customers")
	public ResponseEntity<List<CustomersDto>> getAllCustomers(){
		List<CustomersDto> custDtoList = customerServices.getAllCustomers();
		
		return new ResponseEntity<List<CustomersDto>> (custDtoList,HttpStatus.OK);
		
	}
	
	//Get CustomerCount  as per ShipmentStatus
	@GetMapping("/customers/shipment/{shipmentStatus}")
	public ResponseEntity<CountDto> getCustomerCountByShipmentStatus(@PathVariable String shipmentStatus){
		CountDto countDto = customerServices.getCustomerCountByShipmentStatus(shipmentStatus);
		return new ResponseEntity<CountDto> (countDto,HttpStatus.OK);
		
	}
	
	//Get Shipment for matching customerId
	@GetMapping("customerss/{customerId}")
	public ResponseEntity<List<ShipmentCustomDto>> getShipmentsByCustomerId(@PathVariable Integer customerId){
		List<ShipmentCustomDto> shimentCustomDtos = customerServices.getShipmentsByCustomerId(customerId);
		return new ResponseEntity<List<ShipmentCustomDto>>(shimentCustomDtos,HttpStatus.OK);
	}
	
	//Get Orders for matching customerId
	@GetMapping("customers/{custId}/order")
	public ResponseEntity<List<OrderDto>> getOrdersBycustomerId(@PathVariable Integer custId){
		               List<OrderDto> orderDtos= customerServices.getOrdersByCustomerId(custId);
		               
		return new ResponseEntity<List<OrderDto>> (orderDtos,HttpStatus.OK);
		
	}
}
