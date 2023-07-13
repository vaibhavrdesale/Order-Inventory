package com.orderinventory.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.OrderDetails;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.OrderStatusCounts;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Stores;
import com.orderinventory.exception.OrdersNotFoundException;
import com.orderinventory.repository.CustomersRepository;
import com.orderinventory.repository.OrderRepository;
import com.orderinventory.repository.StoreRepository;
import com.orderinventory.services.OrderServices;

@Service
public class OrderServicesImpl implements OrderServices {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private StoreRepository storesRepository;

	@Override
	public OrderDto saveOrder(OrderDto orderDto) throws OrdersNotFoundException {

		if (orderDto == null) {
			throw new OrdersNotFoundException("Order cannot be null");
		}

		if (storesRepository.existsById(orderDto.getStoreId())) {
			Orders order = new Orders();
			order.setStore(storesRepository.findById(orderDto.getStoreId()).get());
			order.setCustomer(customersRepository.findById(orderDto.getCustomerId()).get());
			BeanUtils.copyProperties(orderDto, order);
			orderRepository.save(order);
			return orderDto;
		}

		throw new OrdersNotFoundException("Store id is not null");
	}

	@Override
	public List<OrderDto> getOrdersByStatus(String orderStatus) {
		List<Orders> orderList = orderRepository.findByOrderStatusContaining(orderStatus);
		System.out.print(orderList);
		if (orderList.isEmpty()) {
			return null;
		}

		List<OrderDto> orderDtos = new ArrayList<>();
		for (Orders order : orderList) {
			OrderDto dto = new OrderDto();
			dto.setCustomerId(order.getCustomer().getCustomerId());
			dto.setStoreId(order.getStore().getStoreId());
			BeanUtils.copyProperties(order, dto);
			orderDtos.add(dto);
		}
		return orderDtos;
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto) throws OrdersNotFoundException {

		if (orderDto == null) {
			throw new OrdersNotFoundException("Order cannot be null");
		}

		if (orderRepository.existsById(orderDto.getOrderId())) {
			Orders order = new Orders();
			BeanUtils.copyProperties(orderDto, order);
			order.setCustomer(customersRepository.findById(orderDto.getCustomerId()).get());
			order.setStore(storesRepository.findByStoreId(orderDto.getStoreId()));
			orderRepository.save(order);
			return orderDto;
		}

		throw new OrdersNotFoundException("No order found at this id");
	}

	@Override
	public List<OrderDto> getAllOrders() throws OrdersNotFoundException {
		List<Orders> orderList = orderRepository.findAll();

		if (orderList.isEmpty()) {
			throw new OrdersNotFoundException("No orders found");
		}

		List<OrderDto> orderDtos = new ArrayList<>();
		for (Orders order : orderList) {
			OrderDto orderDto = new OrderDto();
			orderDto.setCustomerId(order.getCustomer().getCustomerId());
			orderDto.setStoreId(order.getStore().getStoreId());
			BeanUtils.copyProperties(order, orderDto);
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}

	// used to get the count of products based on there status
	@Override
	public OrderStatusCounts getProductCountByStatus() throws OrdersNotFoundException {
		List<Object[]> objectList = orderRepository.getOrderStatusCounts();
		if (!objectList.isEmpty()) {
			Object[] object = objectList.get(0);
			// creating new custom object to send data
			OrderStatusCounts counts = new OrderStatusCounts();
			counts.setCompleteCount((Long) object[0]);
			counts.setCancelledCount((Long) object[1]);
			return counts;
		}

		throw new OrdersNotFoundException("No Counts found");
	}

	@Override

	public List<OrderDetails> getOrderDetailsByStoreName(String storeName) throws OrdersNotFoundException {

		Stores store = storesRepository.findByStoreName(storeName);
		
		if(store == null) {
			throw new OrdersNotFoundException("No store found for this store name");
		}

		List<Orders> orders = orderRepository.findByStore(store);

		List<OrderDetails> orderDetailList = new ArrayList<>();

		for (Orders order : orders) {
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setOrderId(order.getOrderId());
			orderDetails.setOrderStatus(order.getOrderStatus());
			orderDetails.setStoreName(store.getStoreName());
			orderDetails.setWebAddress(store.getWebAddress());
			orderDetailList.add(orderDetails);
		}

		return orderDetailList;

	}

}
