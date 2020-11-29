package com.demo.demoapi.services;


import javax.transaction.Transactional;

import com.demo.demoapi.models.entity.Author;
import com.demo.demoapi.models.repo.JpaAuthorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthorService {
    @Autowired
    private JpaAuthorRepo repo;

    public Author createOne(Author author) throws Exception {
        if(repo.findByEmail(author.getEmail()) == null){
            return repo.save(author);
        }else{
            throw new Exception("Email already used");
        }
    }

    public Iterable<Author> findAll(){
        return repo.findAll();
    }

    public Author findById(int id){
        return repo.findById(id).get();
    }

}
