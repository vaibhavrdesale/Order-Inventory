package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.CountDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.ResponseDto;
import com.orderinventory.dto.ShipmentCustomDto;

public interface CustomersServices {
	
	public ResponseDto saveCustomers(CustomersDto customersDto);  //Add new Customer Object in DB
	
    public List<CustomersDto> getCustomersByFullName(String fullname); //Search Customers by full name
    
    public List<CustomersDto> getCustomersByEmail(String email);  //Search Customers by email
        
    public ResponseDto updateCustomer(CustomersDto customersDto);
    
    public List<CustomersDto> getAllCustomers();
    
    public CountDto getCustomerCountByShipmentStatus(String shipmentStatus);
    
    public List<ShipmentCustomDto> getShipmentsByCustomerId(Integer customerId);
    
    public List<OrderDto> getOrdersByCustomerId(Integer customerId);
    
    
}
