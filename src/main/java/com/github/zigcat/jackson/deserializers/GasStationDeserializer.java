package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.ormlite.models.GasStation;

import java.io.IOException;

public class GasStationDeserializer extends StdDeserializer<GasStation> {
    protected GasStationDeserializer(Class<?> vc) {
        super(vc);
    }

    protected GasStationDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected GasStationDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public GasStationDeserializer(){
        super(GasStation.class);
    }

    @Override
    public GasStation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        String name = json.get("name").asText();
        String address = json.get("address").asText();
        return new GasStation(id, name, address);
    }
}
