package com.orderinventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Stores;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

	public List<Orders> findByOrderStatusContaining(String status);

	public List<Orders> findByStore(Stores store);

	@Query("SELECT COUNT(CASE WHEN o.orderStatus = 'COMPLETE' THEN 1 END) AS completeCount, "
			+ "COUNT(CASE WHEN o.orderStatus = 'CANCELLED' THEN 1 END) AS cancelledCount " + "FROM Orders o")
	public List<Object[]> getOrderStatusCounts();

	public List<Orders> findByCustomer(Customers customer);

}
