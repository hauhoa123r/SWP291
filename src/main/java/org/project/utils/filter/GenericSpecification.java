package org.project.utils.filter;

import jakarta.persistence.criteria.*;
import lombok.*;
import org.project.exception.ResourceUnsupportedException;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class GenericSpecification<T> implements Specification<T> {
    private SearchCriteria searchCriteria;

    private <NumberType extends Number> NumberType convertNumberValue(Object value, Class<NumberType> targetType) {
        if (value == null) return null;

        Number numValue = (Number) value;
        if (targetType == Double.class) {
            return (NumberType) Double.valueOf(numValue.doubleValue());
        } else if (targetType == Long.class) {
            return (NumberType) Long.valueOf(numValue.longValue());
        } else if (targetType == Integer.class) {
            return (NumberType) Integer.valueOf(numValue.intValue());
        } else if (targetType == Float.class) {
            return (NumberType) Float.valueOf(numValue.floatValue());
        }

        return (NumberType) numValue;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        // Tạo đường dẫn truy cập thuộc tính
        Path<Object> realPath;

        if (searchCriteria.getFieldName().contains(".")) {
            String[] fieldParts = searchCriteria.getFieldName().split("\\.");
            realPath = root.get(fieldParts[0]);
            for (int i = 1; i < fieldParts.length; i++) {
                realPath = realPath.get(fieldParts[i]);
            }
        } else {
            realPath = root.get(searchCriteria.getFieldName());
        }

        switch (searchCriteria.getOperation()) {
            case EQUALS -> {
                return builder.equal(realPath, searchCriteria.getComparedValue());
            }
            case NOT_EQUALS -> {
                return builder.notEqual(realPath, searchCriteria.getComparedValue());
            }
            case GREATER_THAN -> {
                return builder.greaterThan(realPath.as(Comparable.class), (Comparable) searchCriteria.getComparedValue());
            }
            case LESS_THAN -> {
                return builder.lessThan(realPath.as(Comparable.class), (Comparable) searchCriteria.getComparedValue());
            }
            case GREATER_THAN_OR_EQUAL_TO -> {
                return builder.greaterThanOrEqualTo(realPath.as(Comparable.class), (Comparable) searchCriteria.getComparedValue());
            }
            case LESS_THAN_OR_EQUAL_TO -> {
                return builder.lessThanOrEqualTo(realPath.as(Comparable.class), (Comparable) searchCriteria.getComparedValue());
            }
            case LIKE -> {
                return builder.like(realPath.as(String.class), searchCriteria.getComparedValue().toString());
            }
            case NOT_LIKE -> {
                return builder.notLike(realPath.as(String.class), searchCriteria.getComparedValue().toString());
            }
            case CONTAINS -> {
                return builder.like(realPath.as(String.class), "%" + searchCriteria.getComparedValue() + "%");
            }
            case NOT_CONTAINS -> {
                return builder.notLike(realPath.as(String.class), "%" + searchCriteria.getComparedValue() + "%");
            }
            case IN -> {
                return realPath.in(searchCriteria.getComparedValue());
            }
            case NOT_IN -> {
                return builder.not(realPath.in(searchCriteria.getComparedValue()));
            }
            case IS_NULL -> {
                return builder.isNull(realPath);
            }
            case IS_NOT_NULL -> {
                return builder.isNotNull(realPath);
            }
            case BETWEEN -> {
                Object[] values = (Object[]) searchCriteria.getComparedValue();
                return builder.between(
                        realPath.as(Comparable.class),
                        (Comparable) values[0],
                        (Comparable) values[1]
                );
            }
            case NOT_BETWEEN -> {
                Object[] values = (Object[]) searchCriteria.getComparedValue();
                return builder.not(
                        builder.between(
                                realPath.as(Comparable.class),
                                (Comparable) values[0],
                                (Comparable) values[1]
                        )
                );
            }

            // Các phép toán AVG
            case AVG_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.equal(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case AVG_NOT_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.notEqual(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case AVG_GREATER_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThan(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case AVG_LESS_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThan(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case AVG_GREATER_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThanOrEqualTo(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case AVG_LESS_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThanOrEqualTo(builder.avg(realPath.as(Double.class)), value));
                return builder.conjunction();
            }

            // Các phép toán COUNT
            case COUNT_EQUALS -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.equal(builder.count(realPath), value));
                return builder.conjunction();
            }
            case COUNT_NOT_EQUALS -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.notEqual(builder.count(realPath), value));
                return builder.conjunction();
            }
            case COUNT_GREATER_THAN -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.greaterThan(builder.count(realPath), value));
                return builder.conjunction();
            }
            case COUNT_LESS_THAN -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.lessThan(builder.count(realPath), value));
                return builder.conjunction();
            }
            case COUNT_GREATER_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.greaterThanOrEqualTo(builder.count(realPath), value));
                return builder.conjunction();
            }
            case COUNT_LESS_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Long value = convertNumberValue(searchCriteria.getComparedValue(), Long.class);
                query.having(builder.lessThanOrEqualTo(builder.count(realPath), value));
                return builder.conjunction();
            }

            // Các phép toán SUM
            case SUM_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.equal(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case SUM_NOT_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.notEqual(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case SUM_GREATER_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThan(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case SUM_LESS_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThan(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case SUM_GREATER_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThanOrEqualTo(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case SUM_LESS_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThanOrEqualTo(builder.sum(realPath.as(Double.class)), value));
                return builder.conjunction();
            }

            // Các phép toán MAX
            case MAX_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.equal(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MAX_NOT_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.notEqual(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MAX_GREATER_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThan(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MAX_LESS_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThan(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MAX_GREATER_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThanOrEqualTo(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MAX_LESS_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThanOrEqualTo(builder.max(realPath.as(Double.class)), value));
                return builder.conjunction();
            }

            // Các phép toán MIN
            case MIN_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.equal(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MIN_NOT_EQUALS -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.notEqual(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MIN_GREATER_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThan(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MIN_LESS_THAN -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThan(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MIN_GREATER_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.greaterThanOrEqualTo(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            case MIN_LESS_THAN_OR_EQUAL_TO -> {
                query.groupBy(root.get("id"));
                Double value = convertNumberValue(searchCriteria.getComparedValue(), Double.class);
                query.having(builder.lessThanOrEqualTo(builder.min(realPath.as(Double.class)), value));
                return builder.conjunction();
            }
            default -> throw new ResourceUnsupportedException("Unsupported search criteria: " + searchCriteria);
        }
    }
}