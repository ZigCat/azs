package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.InvalidDataException;
import com.github.zigcat.exceptions.RedirectException;
import com.github.zigcat.ormlite.models.Column;
import com.github.zigcat.ormlite.models.GasStation;

import java.io.IOException;
import java.sql.SQLException;

public class ColumnDeserializer extends StdDeserializer<Column> {
    protected ColumnDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ColumnDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ColumnDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public ColumnDeserializer(){
        super(Column.class);
    }

    @Override
    public Column deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        String number = json.get("number").asText();
        int gsId = json.get("gasStation").asInt();
        try {
            GasStation gs = GasStation.controller.getDao().queryForId(gsId);
            if(gs == null){
                throw new InvalidDataException("Gas Station must be specified(400)");
            }
            return new Column(id, number, gs);
        } catch (SQLException e) {
            throw new RedirectException("SQLException e");
        }
    }
}
