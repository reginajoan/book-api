package com.demo.demoapi.models.repo;

import java.util.List;

import com.demo.demoapi.models.entity.Book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JpaBookRepo extends PagingAndSortingRepository<Book, Integer> {
    
    public Book findByCode(String code);

    public List<Book> findByTitleLike(String title);

    public List<Book> findByPriceBetween(double priceStart, double priceEnd);

    @Query("SELECT b FROM Book b WHERE b.author= :name")
    public List<Book> findByAuthorName(@Param("name") String name);

    public List<Book> findByCategoryId(int categoryId);
}
