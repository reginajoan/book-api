package com.demo.demoapi.models.repo;
import com.demo.demoapi.models.entity.Category;
import org.springframework.data.repository.CrudRepository;
public interface JpaCategoryRepo extends CrudRepository<Category, Integer> {
    
}
