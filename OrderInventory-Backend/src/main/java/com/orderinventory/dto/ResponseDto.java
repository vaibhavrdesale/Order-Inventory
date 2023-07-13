package com.orderinventory.dto;

public class ResponseDto {
	public String responseMessage;

	public ResponseDto() {

		super();

	}

	public ResponseDto(String responseMessage) {

		super();

		this.responseMessage = responseMessage;

	}

	public String getResponseMessage() {

		return responseMessage;

	}

	public void setResponseMessage(String responseMessage) {

		this.responseMessage = responseMessage;

	}

	@Override

	public String toString() {

		return "ResponseDto [responseMessage=" + responseMessage + "]";

	}

}
