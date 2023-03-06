package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.exceptions.NoAccessException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Map;

public class Controller<T> {
    private Class<T> valueClass;
    private Logger l;
    private Dao<T, Integer> dao;
    private Service service;

    public Controller(){}

    public Controller(Class<T> valueClass){
        this.valueClass = valueClass;
        this.l = LoggerFactory.getLogger(Controller.class);
        service = new Service();
        initDao(valueClass);
    }

    public void get(Context ctx, ObjectMapper om){
        l.info("get for "+getValueClass().getName());
        Map map = ctx.queryParamMap();
        try {
            if(map.containsKey("id")){
                l.info("getting by id");
                ctx.result(om.writeValueAsString(service.getById(getDao(), Integer.parseInt(map.get("id").toString()))));
                ctx.status(200);
                l.info(Security.ok);
            } else {
                l.info("getting all");
                ctx.result(om.writeValueAsString(service.listAll(getDao())));
                ctx.status(200);
                l.info(Security.ok);
            }
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result(Security.serverErrorMessage);
            l.warn(Security.serverErrorMessage);
        }
    }

    public void create(Context ctx, ObjectMapper om){
        l.info("CREATING INSTANCE OF "+getValueClass().getName());
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            User user = Security.authorize(login, password);
            if(user != null){
                if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.OWNER)){
                    String body = ctx.body();
                    getDao().create(om.readValue(body, getValueClass()));
                    ctx.status(200);
                    ctx.result(Security.ok);
                    l.info("INSTANCE OF "+getValueClass().getName()+" CREATED");
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

    public void update(Context ctx, ObjectMapper om){
        l.info("UPDATING INSTANCE OF "+getValueClass().getName());
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            User user = Security.authorize(login, password);
            if(user != null){
                if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.OWNER)){
                    String body = ctx.body();
                    getDao().update(om.readValue(body, getValueClass()));
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

    public void delete(Context ctx, ObjectMapper om){
        l.info("DELETING INSTANCE OF "+getValueClass().getName());
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        Map map = ctx.queryParamMap();
        try {
            User user = Security.authorize(login, password);
            if(user != null){
                if(user.getRole().equals(Role.ADMIN)){
                    if(map.containsKey("id")){
                        getDao().deleteById(Integer.parseInt(map.get("id").toString()));
                        ctx.status(200);
                        ctx.result(Security.ok);
                        l.warn("INSTANCE OF "+getValueClass().getName()+" DELETED");
                    }
                } else {
                    throw new NoAccessException("You haven't permission for this action");
                }
            } else {
                throw new NullPointerException("Unauthorized access restricted");
            }
        } catch (SQLException e) {
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

    private void initDao(Class<T> valueClass){
        try {
            setDao(DaoManager.createDao(DatabaseConfiguration.connectionSource, valueClass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Class<T> getValueClass() {
        return valueClass;
    }

    public void setValueClass(Class<T> valueClass) {
        this.valueClass = valueClass;
    }

    public Logger getL() {
        return l;
    }

    public void setL(Logger l) {
        this.l = l;
    }

    public Dao<T, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<T, Integer> dao) {
        this.dao = dao;
    }
}
