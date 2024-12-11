package com.pos.point_of_sale.common.converters;

import com.pos.point_of_sale.enums.GenderEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderAttributeConverter implements AttributeConverter<GenderEnum, String> {
    
    @Override
    public String convertToDatabaseColumn(GenderEnum gender) {
        if (gender == null) {
            return null;
        }
        return gender.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return GenderEnum.fromValue(dbData);
    }
}
