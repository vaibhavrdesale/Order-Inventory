package com.orderinventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderinventory.dto.InventoryDetailsDto;
import com.orderinventory.entity.Inventory;
import com.orderinventory.entity.Stores;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

//	@Query("SELECT i FROM Inventory i WHERE i.store.id = :storeId")
//	List<Inventory> findByStoreId(@PathVariable("storeId") Integer storeId);

	
	@Query("SELECT i FROM Inventory i WHERE i.store.id = :storeId")
	List<Inventory> findByStoreId(@Param("storeId") Integer storeId);


	@Query("SELECT DISTINCT i FROM Inventory i INNER JOIN i.store s INNER JOIN s.shipments sh WHERE sh.deliveryAddress = :deliveryAddress")
	List<Inventory> findAllWithShipmentsByDeliveryAddress(String deliveryAddress);

	// 3rd end point
	Optional<Inventory> findByStoreOrdersOrderId(Integer orderId);

	// count products by shipment status

	@Query("SELECT sh.shipmentStatus AS shipmentStatus, COUNT(i) AS count "
			+ "FROM Inventory i JOIN i.store s JOIN s.shipments sh " + "WHERE sh.shipmentStatus = :shipmentStatus "
			+ "GROUP BY sh.shipmentStatus")
	List<Object[]> countProductsByShipmentStatus(@Param("shipmentStatus") String shipmentStatus);

	/// api/v1/inventory/{orderid}/details
	@Query("SELECT new com.orderinventory.dto.InventoryDetailsDto(p.productName, p.unitPrice, s.storeName, s.webAddress, s.physicalAddress, sh.shipmentStatus, SUM(p.unitPrice * i.productInventory)) "
			+ "FROM Inventory i " + "JOIN i.product p " + "JOIN i.store s " + "JOIN s.shipments sh "
			+ "JOIN s.orders o " + "WHERE o.orderId = :orderId "
			+ "GROUP BY p.productName, p.unitPrice, s.storeName, s.webAddress, s.physicalAddress, sh.shipmentStatus")
	List<InventoryDetailsDto> getInventoryDetailsByOrderId(@Param("orderId") Integer orderId);
	
	List<Inventory> findByStore(Stores store);

}
