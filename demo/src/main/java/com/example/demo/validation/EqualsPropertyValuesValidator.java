package com.example.demo.validation;

import com.example.demo.validation.annotation.EqualsPropertyValues;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsPropertyValuesValidator
            implements ConstraintValidator<EqualsPropertyValues, Object> {

    private String property;
    private String comparingProperty;
    private String message;

    public void initialize(EqualsPropertyValues constraintAnnotation) {
        this.property = constraintAnnotation.property();
        this.comparingProperty = constraintAnnotation.comparingProperty();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object propertyValue = beanWrapper.getPropertyValue(property);
        Object comparingPropertyValue = beanWrapper.getPropertyValue(comparingProperty);
        boolean matched = ObjectUtils.nullSafeEquals(propertyValue, comparingPropertyValue);
        if (matched) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addPropertyNode(property).addConstraintViolation();
        return false;
    }
}
