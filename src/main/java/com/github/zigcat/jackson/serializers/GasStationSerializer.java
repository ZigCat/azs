package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.GasStation;

import java.io.IOException;

public class GasStationSerializer extends StdSerializer<GasStation> {
    protected GasStationSerializer(Class<GasStation> t) {
        super(t);
    }

    protected GasStationSerializer(JavaType type) {
        super(type);
    }

    protected GasStationSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected GasStationSerializer(StdSerializer<?> src) {
        super(src);
    }

    public GasStationSerializer(){
        super(GasStation.class);
    }

    @Override
    public void serialize(GasStation gasStation, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", gasStation.getId());
        json.writeStringField("name", gasStation.getName());
        json.writeStringField("address", gasStation.getAddress());
        json.writeEndObject();
    }
}
