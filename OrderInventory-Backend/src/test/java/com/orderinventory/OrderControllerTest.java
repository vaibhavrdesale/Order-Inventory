package com.orderinventory;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinventory.dto.OrderDetails;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.OrderStatusCounts;
import com.orderinventory.exception.OrdersNotFoundException;
import com.orderinventory.services.OrderServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "vaibhav@gmail.com")
public class OrderControllerTest {

	@MockBean
	private OrderServices orderServices;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSaveOrders() throws Exception {

		// Create a sample OrderDto
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderId(1);
		orderDto.setCustomerId(2);
		orderDto.setOrderStatus("COMPLETE");
		orderDto.setOrderTms(Timestamp.valueOf("2023-01-03 00:00:00.0"));
		orderDto.setStoreId(4);

		// Define the expected behavior of the mock OrderService
		when(orderServices.saveOrder(orderDto)).thenReturn(orderDto);

		// Perform the HTTP POST request to the controller endpoint
		mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(orderDto)))
				.andExpect(status().isOk());

	}

	@Test
	void updateOrder() throws Exception {

		OrderDto existingOrder = new OrderDto();
		existingOrder.setOrderId(1);
		existingOrder.setCustomerId(2);
		existingOrder.setOrderStatus("COMPLETE");
		existingOrder.setOrderTms(Timestamp.valueOf("2023-01-03 00:00:00.0"));
		existingOrder.setStoreId(4);

		OrderDto updatedOrder = new OrderDto();
		updatedOrder.setOrderId(1);
		updatedOrder.setCustomerId(4);
		updatedOrder.setOrderStatus("CANCELLED");
		updatedOrder.setOrderTms(Timestamp.valueOf("2023-01-04 00:00:00.0"));
		updatedOrder.setStoreId(4);

		when(orderServices.updateOrder(updatedOrder)).thenReturn(updatedOrder);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(updatedOrder))).andExpect(status().isOk());
	}

	@Test
	void testGetOrdersByStatus() throws Exception {
		String orderStatus = "COMPLETE";
		OrderDto orderDto = new OrderDto();

		orderDto.setOrderStatus(orderStatus);

		List<OrderDto> orderDtos = Arrays.asList(orderDto);

		when(orderServices.getOrdersByStatus(orderStatus)).thenReturn(orderDtos);

		mockMvc.perform(get("/api/v1/orderstatus/{orderStatus}", orderStatus)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].orderStatus").value(orderStatus));

		verify(orderServices, times(1)).getOrdersByStatus(orderStatus);

	}

	@Test
	public void testGetAllOrders() throws Exception {
		OrderDto orderDto = new OrderDto();
		orderDto.setCustomerId(1);
		orderDto.setOrderId(2);
		orderDto.setOrderStatus("COMPLETE");
		orderDto.setOrderTms(Timestamp.valueOf("2023-01-03 00:00:00.0"));
		orderDto.setStoreId(4);

		List<OrderDto> orderDtos = Arrays.asList(orderDto);

		when(orderServices.getAllOrders()).thenReturn(orderDtos);

		mockMvc.perform(get("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerId", is(1))).andExpect(jsonPath("$[0].orderId", is(2)))
				.andExpect(jsonPath("$[0].orderStatus", is("COMPLETE")));

		verify(orderServices, times(1)).getAllOrders();
	}

	@Test
	public void testGetOrderCountByStatus() throws Exception {
		// Mock the service method's behavior
		OrderStatusCounts counts = new OrderStatusCounts();
		counts.setCompleteCount(5L);
		counts.setCancelledCount(3L);
		when(orderServices.getProductCountByStatus()).thenReturn(counts);

		// Invoke the controller method
		mockMvc.perform(get("/api/v1/orders/status")).andExpect(status().isOk())
				.andExpect(jsonPath("$.completeCount").value(5L)).andExpect(jsonPath("$.cancelledCount").value(3L));

		verify(orderServices, times(1)).getProductCountByStatus();
	}

	@Test
	public void testGetOrderDetailsByStoreName() throws Exception {
		// Mock data
		String storeName = "exampleStore";
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setStoreName(storeName);
		orderDetails.setOrderId(1);
		orderDetails.setOrderStatus("COMPLETE");
		orderDetails.setWebAddress("http://www.google.com");

		List<OrderDetails> mockOrderDetailsList = new ArrayList<>();
		mockOrderDetailsList.add(orderDetails);

		// Mock dependencies
		when(orderServices.getOrderDetailsByStoreName(storeName)).thenReturn(mockOrderDetailsList);

		mockMvc.perform(get("/api/v1/orders/{storeName}", storeName)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].storeName").value(storeName));

		verify(orderServices, times(1)).getOrderDetailsByStoreName(storeName);

	}

	// Negative test for save order with null values
	@Test
	void testSaveOrdersWithNullOrderDto() throws Exception {
		// Perform the HTTP POST request to the controller endpoint
		mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Disabled
	void testUpdateOrderWithNonExistingOrder() throws Exception {
		OrderDto updatedOrder = new OrderDto();
		updatedOrder.setOrderId(1);
		updatedOrder.setCustomerId(4);
		updatedOrder.setOrderStatus("CANCELLED");
		updatedOrder.setOrderTms(Timestamp.valueOf("2023-01-04 00:00:00.0"));
		updatedOrder.setStoreId(4);

		when(orderServices.updateOrder(updatedOrder))
				.thenThrow(new OrdersNotFoundException("No order found at this id"));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(updatedOrder))).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateOrderWithNullOrderDto() throws Exception {
	    when(orderServices.updateOrder(null)).thenThrow(new OrdersNotFoundException("OrderDto cannot be null"));

	    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(null)))
	            .andExpect(status().isBadRequest());
	}

	@Test
	void testGetOrdersByStatusWithNoOrdersFound() throws Exception {
		String orderStatus = "IN_PROGRESS";

		when(orderServices.getOrdersByStatus(orderStatus)).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/v1/orderstatus/{orderStatus}", orderStatus)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());

		verify(orderServices, times(1)).getOrdersByStatus(orderStatus);
	}

	@Test
	void testGetAllOrdersWithNoOrdersFound() throws Exception {
	    when(orderServices.getAllOrders()).thenThrow(new OrdersNotFoundException("No orders found"));

	    mockMvc.perform(get("/api/v1/orders").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());

	    verify(orderServices, times(1)).getAllOrders();
	}

	@Test
	void testGetOrderCountByStatusWithNoCountsFound() throws Exception {
	    when(orderServices.getProductCountByStatus()).thenThrow(new OrdersNotFoundException("No counts found"));

	    mockMvc.perform(get("/api/v1/orders/status"))
	            .andExpect(status().isNotFound());

	    verify(orderServices, times(1)).getProductCountByStatus();
	}
	
	@Test
	void testGetOrderDetailsByStoreNameWithNoStoreFound() throws Exception {
	    String storeName = "nonExistingStore";

	    when(orderServices.getOrderDetailsByStoreName(storeName))
	            .thenThrow(new OrdersNotFoundException("No store found for this store name"));

	    mockMvc.perform(get("/api/v1/orders/{storeName}", storeName))
	            .andExpect(status().isNotFound());

	    verify(orderServices, times(1)).getOrderDetailsByStoreName(storeName);
	}

	
	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
