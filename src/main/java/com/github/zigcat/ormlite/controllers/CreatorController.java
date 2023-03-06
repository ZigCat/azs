package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.exceptions.NoAccessException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CreatorController extends Controller<User> {
    private Logger l;
    public CreatorController(){
        super(User.class);
        l = LoggerFactory.getLogger(CreatorController.class);
    }

    @Override
    public void create(Context ctx, ObjectMapper om){
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            User user = Security.authorize(login, password);
            if(user != null){
                if(user.getRole().equals(Role.ADMIN)){
                    getDao().create(om.readValue(ctx.body(), User.class));
                    ctx.result(Security.ok);
                    ctx.status(200);
                    l.info("USER CREATED WITH ADMIN ACCESS");

                } else {
                    throw new NoAccessException("You haven't permission for this action");
                }
            } else {
                User u = om.readValue(ctx.body(), User.class);
                if(u.getRole().equals(Role.ADMIN) || u.getRole().equals(Role.OWNER)){
                    throw new NoAccessException("You haven't permission for this action");
                } else {
                    getDao().create(u);
                    ctx.result(Security.ok);
                    ctx.status(200);
                    l.info("USER CREATED");
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            l.warn(Security.serverErrorMessage);
            ctx.result(Security.serverErrorMessage);
            ctx.status(500);
            e.printStackTrace();
        } catch (NoAccessException e) {
            l.warn(Security.noPermission);
            ctx.result(Security.noPermission);
            ctx.status(403);
            e.printStackTrace();
        }
    }
}
