package org.project.admin.converter;

import org.project.admin.enums.patients.BloodType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class BloodTypeConverter implements AttributeConverter<BloodType, String> {
    @Override
    public String convertToDatabaseColumn(BloodType bloodType) {
        if (bloodType == null) return null;
        return bloodType.getValue();
    }

    @Override
    public BloodType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Stream.of(BloodType.values())
                .filter(bt -> bt.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown blood type: " + dbData));
    }
}
