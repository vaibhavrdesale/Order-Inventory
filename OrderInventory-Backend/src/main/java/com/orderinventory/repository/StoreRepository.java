package com.orderinventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderinventory.entity.Inventory;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Stores;

@Repository
public interface StoreRepository extends JpaRepository<Stores, Integer> {

	public Stores findByStoreName(String storeName);
	
	public Stores findByStoreId(Integer storeId);
	
	public List<Orders> getOrdersByStoreId(Long storeId);

//	public Optional<Stores> findByWebAddress(Integer storeId,String webAddress);
	List<Inventory> findInventoryByStoreId(int storeId);

}
