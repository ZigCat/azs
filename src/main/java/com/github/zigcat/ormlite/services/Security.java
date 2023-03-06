package com.github.zigcat.ormlite.services;

import com.github.zigcat.ormlite.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class Security {
    public static String ok = "Success (200)";
    public static String serverErrorMessage = "Internal Server Error (500)";
    public static String noPermission = "You have no permission for this (403)";
    public static String unauthorized = "Unauthorized access (401)";

    public static User authorize(String login, String password) throws SQLException {
        for(User u: User.userDao.queryForAll()){
            if(u.getPhoneNumber().equals(login) && BCrypt.checkpw(password, u.getPassword())){
                return u;
            }
        }
        return null;
    }
}
