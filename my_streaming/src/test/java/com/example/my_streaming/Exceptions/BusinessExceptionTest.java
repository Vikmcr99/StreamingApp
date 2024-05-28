package com.example.my_streaming.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {
    @Test
    void testDefaultConstructor() {
        BusinessException exception = new BusinessException();
        assertNotNull(exception);
        assertTrue(exception.getErrors().isEmpty());
    }

    @Test
    void testSingleValidationErrorConstructor() {
        BusinessValidation validation = new BusinessValidation("Field", "Error message");
        BusinessException exception = new BusinessException(validation);
        assertNotNull(exception);
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains(validation));
    }

    @Test
    void testAddError() {
        BusinessException exception = new BusinessException();
        BusinessValidation validation = new BusinessValidation("Field", "Error message");
        exception.addError(validation);
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains(validation));
    }

    @Test
    void testValidateAndThrowWithErrors() {
        BusinessException exception = new BusinessException();
        BusinessValidation validation = new BusinessValidation("Field", "Error message");
        exception.addError(validation);
        assertThrows(BusinessException.class, exception::validateAndThrow);
    }

    @Test
    void testValidateAndThrowWithoutErrors() {
        BusinessException exception = new BusinessException();
        assertDoesNotThrow(exception::validateAndThrow);
    }

}