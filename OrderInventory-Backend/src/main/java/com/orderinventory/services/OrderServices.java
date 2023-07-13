package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.OrderDetails;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.OrderStatusCounts;
import com.orderinventory.exception.OrdersNotFoundException;

public interface OrderServices {

	public OrderDto saveOrder(OrderDto orderDto) throws OrdersNotFoundException;

	public List<OrderDto> getOrdersByStatus(String orderStatus);

	public List<OrderDto> getAllOrders() throws OrdersNotFoundException;

	public OrderDto updateOrder(OrderDto orderDto) throws OrdersNotFoundException;

	public OrderStatusCounts getProductCountByStatus() throws OrdersNotFoundException;

	public List<OrderDetails> getOrderDetailsByStoreName(String storeName) throws OrdersNotFoundException;

}
