package com.demo.demoapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.demo.demoapi.models.entity.Author;
import com.demo.demoapi.models.entity.Category;


public class BookData {

    @NotEmpty(message = "Code is required")
    @Size(min = 5, message = "Code length must be 5 characters")
    @Pattern(regexp="BK[0-9]+", message = "Code must start with BK")
    private String code;
    
    @NotEmpty(message = "Title is required")
    private String title;

    private Author author;

    @NotEmpty(message = "Description is required")
    private String description;
    
    private double price;
    private Category category;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
