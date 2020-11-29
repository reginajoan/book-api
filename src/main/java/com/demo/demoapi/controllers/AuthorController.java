package com.demo.demoapi.controllers;

import javax.validation.Valid;

import com.demo.demoapi.dto.AuthorData;
import com.demo.demoapi.dto.ResponseData;
import com.demo.demoapi.models.entity.Author;
import com.demo.demoapi.services.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
     @Autowired
     private AuthorService service;

     @GetMapping
     public ResponseEntity<?> findAll(){
        ResponseData response = new ResponseData();
        try{
            response.setPayload(service.findAll());
            response.setStatus(true);
            response.getMessage().add("Load all book");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
     }
     
     @PostMapping
    public ResponseEntity<?> createOne(@Valid @RequestBody AuthorData authorData, Errors error){
        ResponseData response = new ResponseData();
        if(!error.hasErrors()){
            try{
                Author author = new Author();
                author.setName(authorData.getName());
                author.setEmail(authorData.getEmail());
                response.setStatus(true);
                response.getMessage().add("Author saved");
                response.setPayload(service.createOne(author));
                return ResponseEntity.ok(response);
            }catch(Exception e){
                response.setStatus(false);
                response.getMessage().add(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }else{
            for(ObjectError err : error.getAllErrors()){
                response.getMessage().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
