package com.orderinventory.exception;

public class ErrorResponse {
	private String errorMessage;

	 

    private String errorDetails;

 

 

 

    public ErrorResponse() {

 

        super();

 

    }

 

 

 

    public ErrorResponse(String errorMessage, String errorDetails) {

 

        super();

 

        this.errorMessage = errorMessage;

 

        this.errorDetails = errorDetails;

 

    }

 

 

 

    public String getErrorMessage() {

 

        return errorMessage;

 

    }

 

 

 

    public void setErrorMessage(String errorMessage) {

 

        this.errorMessage = errorMessage;

 

    }

 

 

 

    public String getErrorDetails() {

 

        return errorDetails;

 

    }

 

 

 

    public void setErrorDetails(String errorDetails) {

 

        this.errorDetails = errorDetails;

 

    }

 

 

 

    @Override

 

    public String toString() {

 

        return "ErrorResponse [errorMessage=" + errorMessage + ", errorDetails=" + errorDetails + "]";

 

    }
	
	
}
