package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "column")
public class Column implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String number;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private GasStation gasStation;

    public static Controller<Column> controller = new Controller<>(Column.class);

    public Column() {
    }

    public Column(int id, String number, GasStation gasStation) {
        this.id = id;
        this.number = number;
        this.gasStation = gasStation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", gasStation=" + gasStation +
                '}';
    }
}
