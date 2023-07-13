package com.orderinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.CustomInventoryDto;
import com.orderinventory.dto.InventoryDetailStoreIdDto;
import com.orderinventory.dto.InventoryDetailsDto;
import com.orderinventory.dto.InventoryDto;
import com.orderinventory.dto.InventoryWithShipmentsDto;
import com.orderinventory.dto.ShipmentStatusCountDTO;
import com.orderinventory.exception.CustomException;
import com.orderinventory.services.InventoryService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
		
	//get all inventories working	
	@GetMapping("/inventory")
	public ResponseEntity<List<InventoryDto>> getAllInventory() {
		try {
			List<InventoryDto> inventoryList = inventoryService.getAllInventory();
			return ResponseEntity.ok(inventoryList);
		} catch (CustomException ex) {
			throw ex; // Let the GlobalRestExceptionHandler handle the exception
		} catch (Exception ex) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
		}
	}

	//Fetch productdata, storedata, orderstatus matching storied
	@GetMapping("/inventory/store/{storeId}")
	public ResponseEntity<List<InventoryDetailStoreIdDto>> getInventoryByStoreId(@PathVariable Integer storeId) {
	    try {
	        List<InventoryDetailStoreIdDto> inventoryDetails = inventoryService.getInventoryDetailsByStoreId(storeId);
	        return ResponseEntity.ok(inventoryDetails);
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}

	
	
	
	
	//Fetch inventories and matching shipments 3rd end point
	@GetMapping("/inventory/shipment")
	public ResponseEntity<List<InventoryWithShipmentsDto>> getInventoryWithShipmentsByDeliveryAddress(@RequestParam String deliveryAddress) {
	    try {
	        List<InventoryWithShipmentsDto> inventoryList = inventoryService.getInventoryWithShipmentsByDeliveryAddress(deliveryAddress);
	        return ResponseEntity.ok(inventoryList);
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}

	
	//Fetch store, product and customer data matching order id 4th end point 
	@GetMapping("/inventory/{orderId}")
	public ResponseEntity<CustomInventoryDto> getInventoryByOrderId(@PathVariable Integer orderId) {
	    try {
	        CustomInventoryDto inventoryDto = inventoryService.getInventoryByOrderId(orderId);
	        return ResponseEntity.ok(inventoryDto);
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}


	// 5th end point Count of orders by status
	@GetMapping("/shipment/count")
	public ResponseEntity<List<ShipmentStatusCountDTO>> countProductsByShipmentStatus(
	        @RequestParam("shipmentStatus") String shipmentStatus) {
	    try {
	        List<ShipmentStatusCountDTO> counts = inventoryService.countProductsByShipmentStatus(shipmentStatus);
	        return ResponseEntity.ok(counts);
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}

	//Fetch order details matching storename 6th end point
	@GetMapping("/inventory/{orderId}/details")
	public ResponseEntity<List<InventoryDetailsDto>> getInventoryDetailsByOrderId(@PathVariable Integer orderId) {
	    try {
	        List<InventoryDetailsDto> inventoryDetails = inventoryService.getInventoryDetailsByOrderId(orderId);
	        return ResponseEntity.ok(inventoryDetails);
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}

}
