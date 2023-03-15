package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectException;
import com.github.zigcat.ormlite.models.Check;
import com.github.zigcat.ormlite.models.Fuel;
import com.github.zigcat.ormlite.models.GasStation;
import com.github.zigcat.ormlite.models.User;

import java.io.IOException;
import java.sql.SQLException;

public class CheckDeserializer extends StdDeserializer<Check> {
    protected CheckDeserializer(Class<?> vc) {
        super(vc);
    }

    protected CheckDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected CheckDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public CheckDeserializer(){
        super(Check.class);
    }

    @Override
    public Check deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        double quantity = json.get("quantity").asDouble();
        int uId = json.get("user").asInt();
        int fId = json.get("fuel").asInt();
        int gsId = json.get("gasStation").asInt();
        try {
            User u = User.controller.getDao().queryForId(uId);
            Fuel f = Fuel.controller.getDao().queryForId(fId);
            GasStation gs = GasStation.controller.getDao().queryForId(gsId);
            return new Check(id, quantity, u, gs, f);
        } catch (SQLException e) {
            throw new RedirectException("SQLException e");
        }
    }
}
