package com.orderinventory.services.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.InventoryProductDTO;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.StoresDto;
import com.orderinventory.entity.Inventory;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Stores;
import com.orderinventory.exception.CustomException;
import com.orderinventory.repository.InventoryRepository;
import com.orderinventory.repository.StoreRepository;
import com.orderinventory.services.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storesRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String saveStore(StoresDto storeDto) {
		try {
			Stores store = modelMapper.map(storeDto, Stores.class);
			Stores savedStore = storesRepository.save(store);
			return "String: Record Created Successfully";
		} catch (Exception ex) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save Store");
		}
	}

	@Override
	public List<StoresDto> getAllStores() {
		try {
			List<Stores> stores = storesRepository.findAll();

			return stores.stream().map(store -> modelMapper.map(store, StoresDto.class)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve all stores");
		}
	}

	@Override
	public StoresDto getStoreById(Integer storeId) {
		try {
			Optional<Stores> optionalStore = storesRepository.findById(storeId);
			if (optionalStore.isPresent()) {
				return modelMapper.map(optionalStore.get(), StoresDto.class);
			} else {
				throw new CustomException(HttpStatus.NOT_FOUND, "Store not found");
			}
		} catch (Exception e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve store");
		}
	}

	@Override
	public StoresDto updateWebAddress(Integer storeId, String webAddress) {
		try {
			Optional<Stores> optionalStore = storesRepository.findById(storeId);
			if (optionalStore.isPresent()) {
				Stores existingStore = optionalStore.get();
				existingStore.setWebAddress(webAddress);
				Stores updatedStore = storesRepository.save(existingStore);
				return modelMapper.map(updatedStore, StoresDto.class);
			} else {
				throw new CustomException(HttpStatus.NOT_FOUND, "Store not found");
			}
		} catch (Exception e) {
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update web address");
		}
	}

	@Override
	public List<OrderDto> getOrdersByStoreId(Long storeId) {
		List<Orders> orderList = storesRepository.getOrdersByStoreId(storeId);
		Type orderDtoListType = new TypeToken<List<OrderDto>>() {
		}.getType();
		return modelMapper.map(orderList, orderDtoListType);
	}

	@Override
	public boolean deleteStoreById(Integer storeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<InventoryProductDTO> getInventoryAndProductsByStoreId(Integer storeId) {
	    try {
	        Optional<Stores> findById = storesRepository.findById(storeId);

	        List<Inventory> findByStore = inventoryRepository.findByStore(findById.get());
	        List<InventoryProductDTO> list = new ArrayList<>();

	        for (Inventory inventory : findByStore) {
	            InventoryProductDTO inventoryWithProductsDto = new InventoryProductDTO();
	            inventoryWithProductsDto.setInventoryId(inventory.getInventoryId());
	            inventoryWithProductsDto.setProductId(inventory.getProduct().getProductId());
	            inventoryWithProductsDto.setProductName(inventory.getProduct().getProductName());
	            inventoryWithProductsDto.setProductDetails(inventory.getProduct().getProductDetails());
	            inventoryWithProductsDto.setUnitPrice(inventory.getProduct().getUnitPrice());

	            list.add(inventoryWithProductsDto);
	        }

	        return list;
	    } catch (Exception e) {
	        throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while retrieving inventory and products");
	    }
	}

}
