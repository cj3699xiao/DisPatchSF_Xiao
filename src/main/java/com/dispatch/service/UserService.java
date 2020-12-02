package com.dispatch.service;
import com.dispatch.dao.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import com.dispatch.entity.User;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public ResponseEntity<String> loginUser(User user) throws JsonProcessingException {
        Map<String, String> loginResponse = new HashMap<>();
            User targetUser = userDao.getUserByEmailId(user.getEmailId());// Here I think should return a user not customer.
            if (targetUser == null) {
              loginResponse.put("message","User NOT exist");
              String json = new ObjectMapper().writeValueAsString(loginResponse);
              return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
            }
        String testPassword = user.getPassword();
            String truePassword = targetUser.getPassword();
            if (truePassword.equals(testPassword)) {
                loginResponse.put("message","login success");
                String json = new ObjectMapper().writeValueAsString(loginResponse);
                return new ResponseEntity<String>(json, HttpStatus.OK);
            } else {
                loginResponse.put("message","Wrong password");
                String json = new ObjectMapper().writeValueAsString(loginResponse);
                return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
            }
    }

    public ResponseEntity<String> addUser(User user) throws JsonProcessingException {
        Map<String, String>  addUserResponse = new HashMap<>();
        try {
            userDao.addUser(user);
            addUserResponse.put("message","sign up success");
            String json = new ObjectMapper().writeValueAsString(addUserResponse);
            return new ResponseEntity<String>(json, HttpStatus.OK);
        } catch (Exception e) {
            addUserResponse.put("message","Add customer failed.");
            String json = new ObjectMapper().writeValueAsString(addUserResponse);
            return new ResponseEntity<String>(json, HttpStatus.OK);
        }
    }


}
