package edu3431.matiukhin.productmanagment.validation;/*
@author sasha
@project springshop
@class ExistsInDatabaseValidator
@version 1.0.0
@since 23.03.2025 - 00 - 38
*/



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ExistsInDatabase constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return false;
        }

        Object entity = entityManager.find(entityClass, id);
        return entity != null;
    }
}
