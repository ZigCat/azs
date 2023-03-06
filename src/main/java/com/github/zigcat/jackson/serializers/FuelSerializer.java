package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Fuel;

import java.io.IOException;

public class FuelSerializer extends StdSerializer<Fuel> {
    protected FuelSerializer(Class<Fuel> t) {
        super(t);
    }

    protected FuelSerializer(JavaType type) {
        super(type);
    }

    protected FuelSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected FuelSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(Fuel fuel, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", fuel.getId());
        json.writeStringField("name", fuel.getName().toString());
        json.writeNumberField("price", fuel.getPrice());
        json.writeObjectField("gasStation", fuel.getGasStation());
        json.writeEndObject();
    }
}
