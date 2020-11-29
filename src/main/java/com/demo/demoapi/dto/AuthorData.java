package com.demo.demoapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AuthorData {

    @NotEmpty(message = "required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
