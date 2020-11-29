package com.demo.demoapi.services;

import java.util.List;

import javax.transaction.Transactional;

import com.demo.demoapi.models.entity.Book;
import com.demo.demoapi.models.repo.JpaBookRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookService {
    @Autowired
    private JpaBookRepo repo;

    public Iterable<Book> findAll(){
        return repo.findAll();
    }

    public Book findById(int id){
        return repo.findById(id).get();
    }

    public Book save(Book book){
        return repo.save(book);
    }

    public void deleteById(int id){
        repo.deleteById(id);
    }

    public Book findByCode(String code){
        return repo.findByCode(code);
    }

    public Iterable<Book> findByTitleLike(String title){
        return repo.findByTitleLike("%"+title+"%");
    }

    public List<Book> findByPriceBetween(double priceStart, double priceEnd){
        return repo.findByPriceBetween(priceStart, priceEnd);
    }

    public List<Book> findByAuthorName(String author){
        return repo.findByAuthorName(author);
    }

    public List<Book> findByCategoryId(int categoryId){
        return repo.findByCategoryId(categoryId);
    }

    public Iterable<Book> findAll(Pageable pageable){
        return repo.findAll(pageable);
    }
}
