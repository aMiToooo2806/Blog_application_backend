package com.amit.BlogApplication.exceptations;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    @Setter
    String resourceName;
    @Setter
    String fieldName;
    Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public void setFieldValue(long fieldValue) {
        this.fieldValue = fieldValue;
    }
}
