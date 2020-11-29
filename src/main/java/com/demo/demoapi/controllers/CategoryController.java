package com.demo.demoapi.controllers;

import javax.validation.Valid;

import com.demo.demoapi.dto.CategoryData;
import com.demo.demoapi.dto.ResponseData;
import com.demo.demoapi.models.entity.Category;
import com.demo.demoapi.models.repo.JpaCategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
    @Autowired
    private JpaCategoryRepo repo;

    @GetMapping
    public ResponseEntity<?> findAll(){//? artinya generic
        ResponseData response = new ResponseData();
        try{
            response.setPayload(repo.findAll());
            response.setStatus(true);
            response.getMessage().add("Load all book");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        ResponseData response = new ResponseData();
        try{
            Category category = repo.findById(id).get();
            if(category != null){
                response.setPayload(category);
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: "+id);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOne(@PathVariable("id") int id){
        ResponseData response = new ResponseData();
        try{
            if(repo.findById(id).get() != null){
                repo.deleteById(id);
                response.setStatus(true);
                response.getMessage().add("Removed Book By Id: "+id);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOne(@Valid @RequestBody CategoryData category, Errors error){
        ResponseData response = new ResponseData();
        if(!error.hasErrors()){
        try{
            Category newCategory = new Category();
            newCategory.setName(category.getName());
            
            response.setStatus(true);
            response.getMessage().add("Book saved");
            response.setPayload(newCategory);
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
