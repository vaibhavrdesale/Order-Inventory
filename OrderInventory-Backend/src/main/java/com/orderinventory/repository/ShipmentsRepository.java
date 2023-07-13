package com.orderinventory.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderinventory.dto.CustomDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Shipments;
import com.orderinventory.entity.Stores;

@Repository
public interface ShipmentsRepository extends JpaRepository<Shipments, Integer> {

	List<Shipments> findByCustomer(Customers customerId);

	@Query("SELECT c.customerId as customerId,c.emailAddress as emailAddress,c.fullName as fullName,COUNT(*) AS totalShipments " + "FROM Shipments s "
			+ "JOIN s.customer c ON s.customer.customerId=c.customerId "
			+ "GROUP BY s.customer.customerId,c.emailAddress,c.fullName " + "ORDER BY totalShipments DESC")
	
	List<CustomDto> getCustomDto(Pageable pageable);
	
    List<Shipments> findByStore(Stores storeId);

	Set<Shipments> findByShipmentStatus(String shipmentStatus);  
	
	
	
	

}