package com.github.zigcat.ormlite.services;

import com.github.zigcat.exceptions.InvalidDataException;
import com.github.zigcat.ormlite.models.User;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Security {
    public static String ok = "Success (200)";
    public static String serverErrorMessage = "Internal Server Error (500)";
    public static String noPermission = "You have no permission for this (403)";
    public static String unauthorized = "Unauthorized access (401)";
    public static Logger l = LoggerFactory.getLogger(Security.class);

    public static User authorize(String login, String password) throws SQLException {
        for(User u: User.controller.getDao().queryForAll()){
            if(u.getPhoneNumber().equals(login) && BCrypt.checkpw(password, u.getPassword())){
                l.info("USER WAS AUTHORIZED");
                return u;
            }
        }
        l.info("NO SUCH USER FOUND");
        return null;
    }

    public static boolean checkPhoneNumberValid(String phoneNumber) {
        try {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phoneNumberProto = phoneNumberUtil.parse(phoneNumber, null);
            return(phoneNumberUtil.isValidNumber(phoneNumberProto));
        } catch (NumberParseException e) {
            throw new InvalidDataException("Invalid phone number(400)");
        }
    }

    public static boolean checkPhoneNumberInUse(String phoneNumber){
        try {
            for(User u: User.controller.getDao().queryForAll()){
                if(u.getPhoneNumber().equals(phoneNumber)){
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
