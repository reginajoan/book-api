package com.demo.demoapi.models.repo;

import com.demo.demoapi.models.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepo extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
