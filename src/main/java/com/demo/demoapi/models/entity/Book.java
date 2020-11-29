package com.demo.demoapi.models.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="tbl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length=10, nullable=false, unique=true)
    private String code;

    @Column(length=200, nullable=false)
    private String title;


    // @Column(length=50, nullable=false)
    // private String author;

    @ManyToOne
    private Author author;

    @Column(length=255)
    private String description;
    
    private double price;


    @ManyToOne
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public Book(){

    }

    public Book(int id, String code, String title, Author author, String description, double price) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @PrePersist
    public void setTimestamp(){
        setCreateAt(new Date());
    }
}
