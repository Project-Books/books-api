package com.karankumar.booksapi.model.award;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AwardNameConverter implements AttributeConverter<AwardName, String> {
    @Override
    public String convertToDatabaseColumn(AwardName attribute) {
        return attribute.getAwardName();
    }

    @Override
    public AwardName convertToEntityAttribute(String dbData) {
        return AwardName.fromString(dbData);
    }
}