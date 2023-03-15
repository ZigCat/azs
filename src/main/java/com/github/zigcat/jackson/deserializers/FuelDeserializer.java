package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectException;
import com.github.zigcat.ormlite.models.Fuel;
import com.github.zigcat.ormlite.models.FuelType;
import com.github.zigcat.ormlite.models.GasStation;

import java.io.IOException;
import java.sql.SQLException;

public class FuelDeserializer extends StdDeserializer<Fuel> {
    protected FuelDeserializer(Class<?> vc) {
        super(vc);
    }

    protected FuelDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected FuelDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public FuelDeserializer(){
        super(Fuel.class);
    }

    @Override
    public Fuel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        FuelType name = FuelType.valueOf(json.get("name").asText());
        double price = json.get("price").asDouble();
        int gsId = json.get("gasStation").asInt();
        try {
            GasStation gs = GasStation.controller.getDao().queryForId(gsId);
            return new Fuel(id, name, price, gs);
        } catch (SQLException e) {
            throw new RedirectException("SQLException e");
        }
    }
}
