package com.github.zigcat.ormlite.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "fuel_column")
public class FuelColumn {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Fuel fuel;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Column column;

    public FuelColumn() {
    }

    public FuelColumn(int id, Fuel fuel, Column column) {
        this.id = id;
        this.fuel = fuel;
        this.column = column;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "FuelColumn{" +
                "id=" + id +
                ", fuel=" + fuel +
                ", column=" + column +
                '}';
    }
}
