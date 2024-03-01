package com.example.my_streaming.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
    private List<BusinessValidation> errors = new ArrayList<>();

    public BusinessException() { }

    public BusinessException(BusinessValidation validation) {
        this.errors.add(validation);
    }

    public void addError(BusinessValidation validation) {
        this.errors.add(validation);
    }

    public void validateAndThrow() throws BusinessException {
        if (!errors.isEmpty())
            throw this;
    }

    public List<BusinessValidation> getErrors() {
        return errors;
    }
}

