package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectException;
import com.github.zigcat.ormlite.models.Column;
import com.github.zigcat.ormlite.models.Fuel;
import com.github.zigcat.ormlite.models.FuelColumn;

import java.io.IOException;
import java.sql.SQLException;

public class FuelColumnDeserializer extends StdDeserializer<FuelColumn> {
    protected FuelColumnDeserializer(Class<?> vc) {
        super(vc);
    }

    protected FuelColumnDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected FuelColumnDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public FuelColumnDeserializer(){
        super(FuelColumn.class);
    }

    @Override
    public FuelColumn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        int fId = json.get("fuel").asInt();
        int cId = json.get("column").asInt();
        try {
            Fuel f = Fuel.controller.getDao().queryForId(fId);
            Column c = Column.controller.getDao().queryForId(cId);
            return new FuelColumn(id, f, c);
        } catch (SQLException e) {
            throw new RedirectException("SQLException e");
        }
    }
}
