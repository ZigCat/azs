package com.github.zigcat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zigcat.jackson.deserializers.*;
import com.github.zigcat.jackson.serializers.*;
import com.github.zigcat.ormlite.models.*;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.config.enableDevLogging();
        app.start(55567);
        //
        ObjectMapper om = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(User.class, new UserSerializer());
        sm.addSerializer(GasStation.class, new GasStationSerializer());
        sm.addSerializer(Fuel.class, new FuelSerializer());
        sm.addSerializer(FuelColumn.class, new FuelColumnSerializer());
        sm.addSerializer(Column.class, new ColumnSerializer());
        sm.addSerializer(Check.class, new CheckSerializer());
        sm.addDeserializer(User.class, new UserDeserializer());
        sm.addDeserializer(GasStation.class, new GasStationDeserializer());
        sm.addDeserializer(Fuel.class, new FuelDeserializer());
        sm.addDeserializer(FuelColumn.class, new FuelColumnDeserializer());
        sm.addDeserializer(Column.class, new ColumnDeserializer());
        sm.addDeserializer(Check.class, new CheckDeserializer());
        om.registerModule(sm);
        //
        app.get("user/", ctx -> User.controller.get(ctx, om));
        app.post("user/", ctx -> User.controller.create(ctx, om));
        app.patch("user/", ctx -> User.controller.update(ctx, om));
        app.delete("user/:id", ctx -> User.controller.delete(ctx, om));
        //
        app.get("station/", ctx -> GasStation.controller.get(ctx, om));
        app.post("station/", ctx -> GasStation.controller.create(ctx, om));
        app.patch("station/", ctx -> GasStation.controller.update(ctx, om));
        app.delete("station/:id", ctx -> GasStation.controller.delete(ctx, om));
        //
        app.get("column/", ctx -> Column.controller.get(ctx, om));
        app.post("column/", ctx -> Column.controller.create(ctx, om));
        app.patch("column/", ctx -> Column.controller.update(ctx, om));
        app.delete("column/:id", ctx -> Column.controller.delete(ctx, om));
        //
        app.get("fuel/", ctx -> Fuel.controller.get(ctx, om));
        app.post("fuel/", ctx -> Fuel.controller.create(ctx, om));
        app.patch("fuel/", ctx -> Fuel.controller.update(ctx, om));
        app.delete("fuel/:id", ctx -> Fuel.controller.delete(ctx, om));
        //
        app.get("regfuel/", ctx -> FuelColumn.controller.get(ctx, om));
        app.post("regfuel/", ctx -> FuelColumn.controller.create(ctx, om));
        app.patch("regfuel/", ctx -> FuelColumn.controller.update(ctx, om));
        app.delete("regfuel/:id", ctx -> FuelColumn.controller.delete(ctx, om));
        //
        app.get("check/", ctx -> Check.controller.get(ctx, om));
        app.post("check/", ctx -> Check.controller.create(ctx, om));
        app.patch("check/", ctx -> Check.controller.update(ctx, om));
        app.delete("check/:id", ctx -> Check.controller.delete(ctx, om));
    }
}
