package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.FuelColumn;

import java.io.IOException;

public class FuelColumnSerializer extends StdSerializer<FuelColumn> {
    protected FuelColumnSerializer(Class<FuelColumn> t) {
        super(t);
    }

    protected FuelColumnSerializer(JavaType type) {
        super(type);
    }

    protected FuelColumnSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected FuelColumnSerializer(StdSerializer<?> src) {
        super(src);
    }

    public FuelColumnSerializer(){
        super(FuelColumn.class);
    }

    @Override
    public void serialize(FuelColumn fuelColumn, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", fuelColumn.getId());
        json.writeObjectField("fuel", fuelColumn.getFuel());
        json.writeObjectField("column", fuelColumn.getColumn());
        json.writeEndObject();
    }
}
