package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) //applies automatically to all BottleType fields
public class BottleTypeConverter implements AttributeConverter<BottleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BottleType attribute) {
        if (attribute == null) return null;
        return attribute.getVolume(); //stores numeric volume in DB
    }

    @Override
    public BottleType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        //finds enum by numeric volume
        for (BottleType type : BottleType.values()) {
            if (type.getVolume() == dbData) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown BottleType volume: " + dbData);
    }
}
