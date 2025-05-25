package edu3431.matiukhin.productincartmanagment.validation;/*
@author sasha
@project springshop
@class ExistsInDatabase
@version 1.0.0
@since 23.03.2025 - 00 - 37
*/


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsInDatabaseValidator.class)
public @interface ExistsInDatabase {
    String message() default "Запис з таким ID не найдена в базі";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> entityClass();
}
