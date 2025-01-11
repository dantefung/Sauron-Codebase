package com.dantefung.serial.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * big decimal to string
 *
 */
public class SerializerStringBigDecimal extends JsonSerializer<BigDecimal> implements ContextualSerializer {

    protected DecimalFormat decimalFormat;

    public SerializerStringBigDecimal() {
    }

    public SerializerStringBigDecimal(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
        } else {
            if (null != decimalFormat) {
                gen.writeString(decimalFormat.format(value));
            } else {
                gen.writeString(value.toPlainString());
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {

        JsonFormat.Value format = findFormatOverrides(prov, property, handledType());
        if (format == null) {
            return this;
        }

        if (format.hasPattern()) {
            DecimalFormat decimalFormat = new DecimalFormat(format.getPattern());
            return new SerializerStringBigDecimal(decimalFormat);
        }

        return this;
    }

    protected JsonFormat.Value findFormatOverrides(SerializerProvider provider,
                                                   BeanProperty prop, Class<?> typeForDefaults) {
        if (prop != null) {
            return prop.findPropertyFormat(provider.getConfig(), typeForDefaults);
        }
        return provider.getDefaultPropertyFormat(typeForDefaults);
    }
}
