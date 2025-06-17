package org.project.exception.sql;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass) {
        super(String.format("Entity of type %s not found", entityClass.getName()));
    }
}
