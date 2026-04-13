package com.pluxurydolo.youtube.validator;

public enum ValidationResult {
    SUCCESS,
    FAILURE;

    public static ValidationResult fromBoolean(boolean result) {
        if (result) {
            return SUCCESS;
        }
        return FAILURE;
    }
}
