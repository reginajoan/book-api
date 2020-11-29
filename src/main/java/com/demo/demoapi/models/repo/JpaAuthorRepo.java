package com.demo.demoapi.models.repo;

import com.demo.demoapi.models.entity.Author;

import org.springframework.data.repository.CrudRepository;

public interface JpaAuthorRepo extends CrudRepository<Author, Integer> {
    public Author findByEmail(String email);
}
