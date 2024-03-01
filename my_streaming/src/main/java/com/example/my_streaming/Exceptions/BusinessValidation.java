package com.example.my_streaming.Exceptions;

public class BusinessValidation {
    private String errorName;
    private String errorDescription;

    public BusinessValidation(String errorName, String errorDescription) {
        this.errorName = errorName;
        this.errorDescription = errorDescription;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
