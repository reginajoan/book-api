package com.demo.demoapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryData {
    @NotEmpty(message = "nama tidak boleh kosong")
    @Size(min = 5, message = "minimal 5 character")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
