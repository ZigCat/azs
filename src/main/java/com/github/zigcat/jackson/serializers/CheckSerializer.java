package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Check;

import java.io.IOException;

public class CheckSerializer extends StdSerializer<Check> {
    protected CheckSerializer(Class<Check> t) {
        super(t);
    }

    protected CheckSerializer(JavaType type) {
        super(type);
    }

    protected CheckSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected CheckSerializer(StdSerializer<?> src) {
        super(src);
    }

    public CheckSerializer(){
        super(Check.class);
    }

    @Override
    public void serialize(Check check, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", check.getId());
        json.writeNumberField("quantity", check.getQuantity());
        json.writeObjectField("gasStation", check.getGasStation());
        json.writeObjectField("user", check.getUser());
        json.writeObjectField("fuel", check.getFuel());
        json.writeEndObject();
    }
}
