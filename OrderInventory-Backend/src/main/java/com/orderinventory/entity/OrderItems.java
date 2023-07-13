//package com.orderinventory.entity;
//
//import java.math.BigDecimal;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="order_items")
//public class OrderItems {
//	
//	@EmbeddedId
//	@Column(name="order_id")
//	private Integer orderId;
//	
//	@Column(name="line_item_id")
//	private Integer lineItemId;
//	
//	@Column(name="product_id")
//	private Integer productId;
//	
//	@Column(name="unit_price")
//	private BigDecimal unitPrice;
//	
//	@Column(name="quantity")
//	private Integer quantity;
//	
//	@Column(name="shipment_id")
//	private Integer shipmentid;
//	
//	
//	public Integer getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(Integer orderId) {
//		this.orderId = orderId;
//	}
//	public Integer getLineItemId() {
//		return lineItemId;
//	}
//	public void setLineItemId(Integer lineItemId) {
//		this.lineItemId = lineItemId;
//	}
//	public Integer getProductId() {
//		return productId;
//	}
//	public void setProductId(Integer productId) {
//		this.productId = productId;
//	}
//	public BigDecimal getUnitPrice() {
//		return unitPrice;
//	}
//	public void setUnitPrice(BigDecimal unitPrice) {
//		this.unitPrice = unitPrice;
//	}
//	public Integer getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
//	public Integer getShipmentid() {
//		return shipmentid;
//	}
//	public void setShipmentid(Integer shipmentid) {
//		this.shipmentid = shipmentid;
//	}
//	
//}
