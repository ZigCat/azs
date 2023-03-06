package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.parameters.Modelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "fuel")
public class Fuel implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private FuelType name;

    @DatabaseField
    private double price;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private GasStation gasStation;

    public Fuel() {
    }

    public Fuel(int id, FuelType name, double price, GasStation gasStation) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.gasStation = gasStation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FuelType getName() {
        return name;
    }

    public void setName(FuelType name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    @Override
    public String toString() {
        return "Fuel{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", gasStation=" + gasStation +
                '}';
    }
}
