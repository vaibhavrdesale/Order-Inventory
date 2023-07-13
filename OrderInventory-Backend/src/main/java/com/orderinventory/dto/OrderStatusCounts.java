package com.orderinventory.dto;

public class OrderStatusCounts {
	private Long completeCount;
	private Long cancelledCount;

	public Long getCompleteCount() {
		return completeCount;
	}

	public void setCompleteCount(Long completeCount) {
		this.completeCount = completeCount;
	}

	public Long getCancelledCount() {
		return cancelledCount;
	}

	public void setCancelledCount(Long cancelledCount) {
		this.cancelledCount = cancelledCount;
	}

}
