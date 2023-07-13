package com.orderinventory.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.orderinventory.dto.InventoryProductDTO;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.StoresDto;

@Service
public interface StoreService {
	
	public String saveStore(StoresDto storeDto);//post method is used
	
	public List<StoresDto> getAllStores();//get all stores
	
	public StoresDto getStoreById(Integer storeId);// get single store
	
	public StoresDto updateWebAddress(Integer storeId,String webAddress); //update web address
	
    public List<OrderDto> getOrdersByStoreId(Long storeId);

	boolean deleteStoreById(Integer storeId);

	List<InventoryProductDTO> getInventoryAndProductsByStoreId(Integer storeId);


	
	
}
