package com.orderinventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.OrderDetails;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.OrderStatusCounts;
import com.orderinventory.exception.OrdersNotFoundException;
import com.orderinventory.services.OrderServices;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

	@Autowired
	private OrderServices orderServices;
	
	//save order api for order
	@PostMapping("/orders")
	public ResponseEntity<OrderDto> saveOrders(@Valid @RequestBody OrderDto orderDto) throws OrdersNotFoundException {
		OrderDto saveOrder = orderServices.saveOrder(orderDto);
		return new ResponseEntity<OrderDto>(saveOrder, HttpStatus.OK);
	}
	
	// getting all orders that matching the respective order status
	@GetMapping("/orderstatus/{orderStatus}")
	public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String orderStatus) {
		List<OrderDto> ordersByStatus = orderServices.getOrdersByStatus(orderStatus);
		return new ResponseEntity<List<OrderDto>>(ordersByStatus, HttpStatus.OK);
	}
	
	//updating order details
	@PutMapping("/orders")
	public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody OrderDto orderDto) throws OrdersNotFoundException {
		OrderDto updatedOrder = orderServices.updateOrder(orderDto);
		return new ResponseEntity<OrderDto>(updatedOrder, HttpStatus.OK);
	}
	
	//get all orders
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDto>> getAllOrders() throws OrdersNotFoundException {
		List<OrderDto> ordersList = orderServices.getAllOrders();
		return new ResponseEntity<List<OrderDto>>(ordersList, HttpStatus.OK);
	}
	
	//getting orders status counts as per status
	@GetMapping("/orders/status")
	public ResponseEntity<OrderStatusCounts> getProductCountByStatus() throws OrdersNotFoundException {
		OrderStatusCounts productCountByStatus = orderServices.getProductCountByStatus();
		return new ResponseEntity<OrderStatusCounts>(productCountByStatus, HttpStatus.OK);
	}
	
	//getting all orders that are matching storename
	@GetMapping("/orders/{storeName}")
	public ResponseEntity<List<OrderDetails>> getOrderDetailsByStoreName(@PathVariable String storeName) throws OrdersNotFoundException {
		List<OrderDetails> orderDetailsList = orderServices.getOrderDetailsByStoreName(storeName);
		return new ResponseEntity<List<OrderDetails>>(orderDetailsList, HttpStatus.OK);
	}
}
