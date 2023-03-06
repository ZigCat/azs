package com.github.zigcat.ormlite.models;

import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "user")
public class User implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String fname;

    @DatabaseField
    private String lname;

    @DatabaseField(unique = true)
    private String phoneNumber;

    @DatabaseField
    private String password;

    @DatabaseField
    private Role role;


    public static Dao<User, Integer> userDao;

    static {
        try {
            userDao = DaoManager.createDao(DatabaseConfiguration.connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User() {
    }

    public User(int id, String fname, String lname, String phoneNumber, String password, Role role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
