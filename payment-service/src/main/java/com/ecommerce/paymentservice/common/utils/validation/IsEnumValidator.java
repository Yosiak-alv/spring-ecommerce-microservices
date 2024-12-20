package com.ecommerce.paymentservice.common.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class IsEnumValidator implements ConstraintValidator<IsEnum, Object> {

    private List<String> values = new ArrayList<>();
    @Override
    public void initialize(IsEnum enumValue) {
        values = new ArrayList<>();
        Enum<?>[] enumConstants = enumValue.enumClass().getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            values.add(enumConstant.name());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null is allowed; use @NotNull for null checks
        }

        if (value instanceof Enum<?>) {
            return values.contains(((Enum<?>) value).name());
        }

        return false;
    }
}