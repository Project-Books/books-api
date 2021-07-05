package com.karankumar.booksapi.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Adding a converter to convert enum to values while inserting and retrieving from Table
 */
@Converter(autoApply = true)
public class AwardNameConverter implements AttributeConverter<AwardName, String> {
    @Override
    public String convertToDatabaseColumn(AwardName attribute) {
        return attribute.getPrizeName();
    }

    @Override
    public AwardName convertToEntityAttribute(String dbData) {
        return AwardName.fromLongName(dbData);
    }
}
