package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.CustomDto;
import com.orderinventory.dto.ShipmentsDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Stores;


public interface ShipmentsServices {
	
	   public String saveShipments(ShipmentsDto shipmentsDto);//Add a Shipment 	
	
       public List<ShipmentsDto> getShipmentsByCustomerId(Customers CustomerId); //Display all Shipment based on a customer
       
       public List<CustomDto> getTopTenMostShippedCustomers();      //Display top 10 most shipped by customers
      
       public List<ShipmentsDto> getShipmentsByStoreId(Stores storeId); //Display shipment details by store ID
       
      
}
