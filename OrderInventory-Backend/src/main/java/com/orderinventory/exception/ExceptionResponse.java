package com.orderinventory.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
	private String errorMessage;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	private Integer errorCode;

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(String errorMessage, LocalDateTime timeStamp, Integer errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.timeStamp = timeStamp;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer geterrorCode() {
		return errorCode;
	}

	public void seterrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
