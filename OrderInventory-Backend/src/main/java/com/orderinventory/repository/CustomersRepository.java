package com.orderinventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderinventory.entity.Customers;
@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
   
	List<Customers> findByFullName(String name);
	
	List<Customers> findByEmailAddress(String email);
	
	Customers findByCustomerId(Integer customerId);

	Boolean existsByEmailAddress(String emailAddress);
	
	@Query("SELECT COUNT(DISTINCT s.customer.customerId) FROM Shipments s WHERE s.shipmentStatus = :status")
     Integer getCustomerCountByShipmentStatus(@Param("status") String shipmentStatus);
	
   
}
