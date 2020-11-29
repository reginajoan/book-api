package com.demo.demoapi.controllers;

import java.util.Base64;

import javax.validation.Valid;

import com.demo.demoapi.dto.LoginData;
import com.demo.demoapi.dto.ResponseData;
import com.demo.demoapi.dto.UserData;
import com.demo.demoapi.helpers.MD5Generator;
import com.demo.demoapi.models.entity.User;
import com.demo.demoapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserData userData, Errors errors){
        ResponseData response = new ResponseData();
        if(!errors.hasErrors()){
            try {
                User user = new User();
                user.setFullName(userData.getFullName());
                user.setEmail(userData.getEmail());
                user.setPassword(MD5Generator.generate(userData.getPassword()));
                response.setStatus(true);
                response.getMessage().add("User created");
                response.setPayload(userService.createOne(user));
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                //TODO: handle exception
                response.setStatus(false);
                response.getMessage().add(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }else{
            for(ObjectError err : errors.getAllErrors()){
                response.getMessage().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData){
        ResponseData response = new ResponseData();
        try {
            User user = userService.login(loginData.getEmail(), MD5Generator.generate(loginData.getPassword()));
            if(user == null){
                response.setStatus(false);
                response.getMessage().add("Login gagal");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
            }
            String baseString = user.getEmail() +":"+user.getPassword();
            String token = Base64.getEncoder().encodeToString(baseString.getBytes());
            response.setStatus(true);
            response.getMessage().add("Login sukses");
            response.setPayload(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
