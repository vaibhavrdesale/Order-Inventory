package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.CustomInventoryDto;
import com.orderinventory.dto.InventoryDetailStoreIdDto;
import com.orderinventory.dto.InventoryDetailsDto;
import com.orderinventory.dto.InventoryDto;
import com.orderinventory.dto.InventoryWithShipmentsDto;
import com.orderinventory.dto.ShipmentStatusCountDTO;

public interface InventoryService {
	List<InventoryDto> getAllInventory();

	List<InventoryDetailStoreIdDto> getInventoryDetailsByStoreId(Integer storeId);

	List<InventoryWithShipmentsDto> getInventoryWithShipmentsByDeliveryAddress(String deliveryAddress);

	CustomInventoryDto getInventoryByOrderId(Integer orderId);

	List<ShipmentStatusCountDTO> countProductsByShipmentStatus(String shipmentStatus);

	List<InventoryDetailsDto> getInventoryDetailsByOrderId(Integer orderId);

}
