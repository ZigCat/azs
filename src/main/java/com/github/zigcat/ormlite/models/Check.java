package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "check")
public class Check implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private double quantity;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private GasStation gasStation;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Fuel fuel;

    public static Controller<Check> controller = new Controller<>(Check.class);

    public Check() {
    }

    public Check(int id, double quantity, User user, GasStation gasStation, Fuel fuel) {
        this.id = id;
        this.quantity = quantity;
        this.user = user;
        this.gasStation = gasStation;
        this.fuel = fuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", user=" + user +
                ", gasStation=" + gasStation +
                ", fuel=" + fuel +
                '}';
    }
}
