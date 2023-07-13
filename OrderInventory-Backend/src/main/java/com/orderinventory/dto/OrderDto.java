package com.orderinventory.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderDto {

	private Integer orderId;
	@NotNull
	private Timestamp orderTms;
	@NotNull
	private Integer customerId;
	@NotBlank
	@Size(max = 255)
	private String orderStatus;
	@NotNull
	private Integer storeId;

	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDto(Integer orderId, Timestamp orderTms, Integer customerId, String orderStatus, Integer storeId) {
		super();
		this.orderId = orderId;
		this.orderTms = orderTms;
		this.customerId = customerId;
		this.orderStatus = orderStatus;
		this.storeId = storeId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Timestamp getOrderTms() {
		return orderTms;
	}

	public void setOrderTms(Timestamp orderTms) {
		this.orderTms = orderTms;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", orderTms=" + orderTms + ", customerId=" + customerId
				+ ", orderStatus=" + orderStatus + ", storeId=" + storeId + "]";
	}

}
