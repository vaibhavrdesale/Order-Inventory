package com.orderinventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.CustomInventoryDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.InventoryDetailStoreIdDto;
import com.orderinventory.dto.InventoryDetailsDto;
import com.orderinventory.dto.InventoryDto;
import com.orderinventory.dto.InventoryWithShipmentsDto;
import com.orderinventory.dto.ProductDto;
import com.orderinventory.dto.ShipmentStatusCountDTO;
import com.orderinventory.dto.ShipmentsDto;
import com.orderinventory.dto.StoresDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Inventory;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Products;
import com.orderinventory.entity.Shipments;
import com.orderinventory.entity.Stores;
import com.orderinventory.exception.CustomException;
import com.orderinventory.repository.InventoryRepository;
import com.orderinventory.services.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private  InventoryRepository inventoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

//	Fetch productdata, storedata, orderstatus matching storied 2nd end point

	@Override
	public List<InventoryDetailStoreIdDto> getInventoryDetailsByStoreId(Integer storeId) {
	    try {
	        List<Inventory> inventoryList = inventoryRepository.findByStoreId(storeId);

	        if (inventoryList.isEmpty()) {
	            throw new CustomException(HttpStatus.NOT_FOUND, "No inventory found for store ID: " + storeId);
	        }

	        return inventoryList.stream()
	                .map(this::mapToInventoryDetailStoreIdDto)
	                .collect(Collectors.toList());
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}


	private InventoryDetailStoreIdDto mapToInventoryDetailStoreIdDto(Inventory inventory) {
		InventoryDetailStoreIdDto inventoryDto = modelMapper.map(inventory, InventoryDetailStoreIdDto.class);

		if (inventory.getProduct() != null) {
			ProductDto productDto = modelMapper.map(inventory.getProduct(), ProductDto.class);
			inventoryDto.setProductData(productDto);
		}

		if (inventory.getStore() != null) {
			StoresDto storeDto = modelMapper.map(inventory.getStore(), StoresDto.class);
			inventoryDto.setStoreData(storeDto);
		}

		String orderStatus = getOrderStatus(inventory); // Call the getOrderStatus method
		inventoryDto.setOrderStatus(orderStatus);

		return inventoryDto;
	}

	private String getOrderStatus(Inventory inventory) {
		if (inventory.getStore() != null && !inventory.getStore().getOrders().isEmpty()) {
			Orders order = inventory.getStore().getOrders().get(0); // Assuming you want the first order
			return order.getOrderStatus();
		}
		return null;
	}

//get all inventories 1st endpoint

	@Override
	public List<InventoryDto> getAllInventory() {
	    List<Inventory> inventoryList = inventoryRepository.findAll();

	    if (inventoryList.isEmpty()) {
	        throw new CustomException(HttpStatus.NOT_FOUND, "No inventory found.");
	    }

	    return inventoryList.stream()
	            .map(this::mapToInventoryDto)
	            .collect(Collectors.toList());
	}

	private InventoryDto mapToInventoryDto(Inventory inventory) {
	    InventoryDto inventoryDto = modelMapper.map(inventory, InventoryDto.class);
	    inventoryDto.getStoreData().setStoreId(inventory.getStore().getStoreId()); 
//	    inventoryDto.getStoreData().setPhysicalAddress(inventory.getStore().getPhysicalAddress());
	    ProductDto productDto = modelMapper.map(inventory.getProduct(), ProductDto.class);
	    productDto.setProductDetails(inventory.getProduct().getProductDetails());
	    inventoryDto.setProductData(productDto);
	    return inventoryDto;
	}


	
	
	// Fetch inventories and matching shipments 3rd end point

	@Override
	public List<InventoryWithShipmentsDto> getInventoryWithShipmentsByDeliveryAddress(String deliveryAddress) {
	    try {
	        List<Inventory> inventoryList = inventoryRepository.findAllWithShipmentsByDeliveryAddress(deliveryAddress);

	        if (inventoryList.isEmpty()) {
	            throw new CustomException(HttpStatus.NOT_FOUND, "No inventory found for the given delivery address.");
	        }

	        return inventoryList.stream()
	                .map(inventory -> mapToInventoryWithShipmentsDto(inventory, deliveryAddress))
	                .collect(Collectors.toList());
	    } catch (CustomException ex) {
	        throw ex; // Let the GlobalRestExceptionHandler handle the exception
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex);
	    }
	}

	private ShipmentsDto mapToShipmentsDtoExcludingOrderStatus(Shipments shipment) {
		ShipmentsDto shipmentsDto = modelMapper.map(shipment, ShipmentsDto.class);

		// Set customer ID
		if (shipment.getCustomer() != null) {
			shipmentsDto.setCustomerId(shipment.getCustomer().getCustomerId());
		}

		return shipmentsDto;
	}

	private InventoryWithShipmentsDto mapToInventoryWithShipmentsDto(Inventory inventory, String deliveryAddress) {
		List<ShipmentsDto> shipmentData = inventory.getStore().getShipments().stream()
				.filter(shipment -> shipment.getDeliveryAddress().equals(deliveryAddress))
				.map(this::mapToShipmentsDtoExcludingOrderStatus).collect(Collectors.toList());

		InventoryWithShipmentsDto inventoryDto = modelMapper.map(inventory, InventoryWithShipmentsDto.class);
		inventoryDto.setShipmentData(shipmentData);

		// Map productData
		Products product = inventory.getProduct();
		if (product != null) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			inventoryDto.setProductData(productDto);
		}

		// Map storeData
		Stores store = inventory.getStore();
		if (store != null) {
			StoresDto storesDto = modelMapper.map(store, StoresDto.class);
			inventoryDto.setStoreData(storesDto);
		}

		return inventoryDto;
	}

	// 4rd end point Fetch store, product and customer data matching order id
	@Override
	public CustomInventoryDto getInventoryByOrderId(Integer orderId) {
	    Optional<Inventory> optionalInventory = inventoryRepository.findByStoreOrdersOrderId(orderId);
	    Inventory inventory = optionalInventory.orElseThrow(
	            () -> new CustomException(HttpStatus.NOT_FOUND, "No inventory found for the given order ID: " + orderId));

	    CustomInventoryDto inventoryDto = new CustomInventoryDto();
	    inventoryDto.setInventoryId(inventory.getInventoryId());
	    inventoryDto.setProductData(convertToProductDto(inventory.getProduct()));
	    inventoryDto.setStoreData(convertToStoreDto(inventory.getStore()));
	    inventoryDto.setCustomerData(convertToCustomerDto(inventory.getStore().getOrders().get(0).getCustomer()));

	    return inventoryDto;
	}


	private ProductDto convertToProductDto(Products product) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	private StoresDto convertToStoreDto(Stores store) {
		StoresDto storesDto = new StoresDto();
		BeanUtils.copyProperties(store, storesDto);
		return storesDto;
	}

	private CustomersDto convertToCustomerDto(Customers customer) {
		CustomersDto customersDto = new CustomersDto();
		BeanUtils.copyProperties(customer, customersDto);
		return customersDto;
	}

// 5th send point	Count shipment status wise count of total products sold   its working dont touch

	@Override
	public List<ShipmentStatusCountDTO> countProductsByShipmentStatus(String shipmentStatus) {
	    try {
	        List<Object[]> results = inventoryRepository.countProductsByShipmentStatus(shipmentStatus);
	        return mapToShipmentStatusCountDTOList(results);
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while counting products by shipment status.", ex);
	    }
	}

	private List<ShipmentStatusCountDTO> mapToShipmentStatusCountDTOList(List<Object[]> results) {
	    List<ShipmentStatusCountDTO> counts = new ArrayList<>();
	    for (Object[] result : results) {
	        String status = (String) result[0];
	        Long count = (Long) result[1];
	        ShipmentStatusCountDTO shipmentStatusCountDTO = new ShipmentStatusCountDTO(status, count.intValue());
	        counts.add(shipmentStatusCountDTO);
	    }
	    return counts;
	}


	// 6th end point Fetch order details matching storename

	@Override
	public List<InventoryDetailsDto> getInventoryDetailsByOrderId(Integer orderId) {
	    try {
	        return inventoryRepository.getInventoryDetailsByOrderId(orderId);
	    } catch (Exception ex) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving inventory details by order ID.", ex);
	    }
	}

}
