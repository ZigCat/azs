package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.InvalidDataException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import org.eclipse.jetty.server.Authentication;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    protected UserDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected UserDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public UserDeserializer(){
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        int id = json.get("id").asInt();
        String fname = json.get("fname").asText();
        String lname = json.get("lname").asText();
        String phoneNumber = json.get("phoneNumber").asText();
        if(!Security.checkPhoneNumberValid(phoneNumber)){
            throw new InvalidDataException("Invalid phone number(400)");
        }
        String password = json.get("password").asText();
        Role role;
        try{
            role = Role.valueOf(json.get("role").asText());
        } catch(NullPointerException e){
            role = Role.USER;
        }
        return new User(id, fname, lname, phoneNumber, BCrypt.hashpw(password, BCrypt.gensalt(12)), role);
    }
}
