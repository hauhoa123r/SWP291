package org.project.exception.mapping;

public class ErrorMappingException extends RuntimeException {
    public ErrorMappingException(Class<?> sourceClass, Class<?> targetClass) {
        super(String.format("Error mapping from %s to %s", sourceClass.getSimpleName(), targetClass.getSimpleName()));
    }
}
