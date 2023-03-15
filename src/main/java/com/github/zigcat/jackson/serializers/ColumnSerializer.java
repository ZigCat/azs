package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Column;

import java.io.IOException;

public class ColumnSerializer extends StdSerializer<Column> {
    protected ColumnSerializer(Class<Column> t) {
        super(t);
    }

    protected ColumnSerializer(JavaType type) {
        super(type);
    }

    protected ColumnSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected ColumnSerializer(StdSerializer<?> src) {
        super(src);
    }

    public ColumnSerializer(){
        super(Column.class);
    }

    @Override
    public void serialize(Column column, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", column.getId());
        json.writeStringField("number", column.getNumber());
        json.writeObjectField("gasStation", column.getGasStation());
        json.writeEndObject();
    }
}
