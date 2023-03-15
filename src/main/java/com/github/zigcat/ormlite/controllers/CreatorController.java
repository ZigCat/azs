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
            User nUser = om.readValue(ctx.body(), User.class);
            if(user != null){
                if(user.getRole().equals(Role.ADMIN)){
                    getDao().create(nUser);
                    ctx.result(Security.ok);
                    ctx.status(200);
                    l.info("USER CREATED WITH ADMIN ACCESS");
                } else if(!user.getRole().equals(Role.ADMIN)
                        && (!nUser.getRole().equals(Role.USER) || nUser.getRole() == null)) {
                    nUser.setRole(Role.USER);
                    getDao().create(nUser);
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

    @Override
    public void update(Context ctx, ObjectMapper om){
        l.info("UPDATING INSTANCE OF USER");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            User user = Security.authorize(login, password);
            User nUser = om.readValue(ctx.body(), User.class);
            if(user != null){
                l.info(nUser.getId() + " " + user.getId());
                if(user.getRole().equals(Role.ADMIN)){
                    getDao().update(nUser);
                    ctx.status(200);
                    ctx.result(Security.ok);
                    l.info("INSTANCE OF "+getValueClass().getName()+" UPDATED as ADMIN");
                } else if(user.getId() == nUser.getId()
                        && (user.getRole().equals(nUser.getRole()) || user.getRole() == null)){
                    nUser.setRole(user.getRole());
                    getDao().update(nUser);
                    ctx.status(200);
                    ctx.result(Security.ok);
                    l.info("INSTANCE OF "+getValueClass().getName()+" UPDATED");
                } else {
                    throw new NoAccessException("You haven't permission for this action");
                }
            } else {
                throw new NullPointerException("Unauthorized access restricted");
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
        } catch (NullPointerException e){
            l.warn(Security.unauthorized);
            ctx.result(Security.unauthorized);
            ctx.status(403);
            e.printStackTrace();
        }
    }
}
