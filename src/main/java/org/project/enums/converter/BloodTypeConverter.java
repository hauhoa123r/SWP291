package org.project.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.project.enums.BloodType;

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
        for (BloodType type : BloodType.values()) {
            if (type.getValue().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + dbData);
    }
}
