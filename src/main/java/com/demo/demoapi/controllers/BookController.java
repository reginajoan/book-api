package com.demo.demoapi.controllers;

import java.util.List;

import javax.validation.Valid;

import com.demo.demoapi.dto.BookData;
import com.demo.demoapi.dto.ResponseData;
import com.demo.demoapi.dto.SearchAuthor;
import com.demo.demoapi.dto.SearchTitle;
import com.demo.demoapi.models.entity.Book;
import com.demo.demoapi.models.entity.Category;
import com.demo.demoapi.models.repo.JpaBookRepo;
import com.demo.demoapi.models.repo.JpaCategoryRepo;
import com.demo.demoapi.services.AuthorService;
import com.demo.demoapi.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    
    @Autowired
    private BookService service;

    @Autowired
    private JpaBookRepo jrepo;

    @Autowired
    private JpaCategoryRepo cRepo;
    
    @Autowired
    private AuthorService authorService;
    
    @GetMapping
    public ResponseEntity<?> findAll(){//? artinya generic
        ResponseData response = new ResponseData();
        try{
            response.setPayload(service.findAll());
            response.setStatus(true);
            response.getMessage().add("Load all book");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        ResponseData response = new ResponseData();
        try{
            Book book = jrepo.findById(id).get();
            if(book != null){
                response.setPayload(book);
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: "+id);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOne(@Valid @RequestBody BookData bookData, Errors error){
        ResponseData response = new ResponseData();
        if(!error.hasErrors()){
            try{
                Book newBook = new Book();
                newBook.setCode(bookData.getCode());
                newBook.setTitle(bookData.getTitle());
                newBook.setDescription(bookData.getDescription());
                newBook.setPrice(bookData.getPrice());
                newBook.setAuthor(authorService.findById(bookData.getAuthor().getId()));
                newBook.setCategory(cRepo.findById(bookData.getCategory().getId()).get());
                newBook = service.save(newBook);
                response.setStatus(true);
                response.getMessage().add("Book saved");
                response.setPayload(newBook);
                return ResponseEntity.ok(response);
            }catch(Exception e){
                response.setStatus(false);
                response.getMessage().add(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }else{
            for(ObjectError err : error.getAllErrors()){
                response.getMessage().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOne(@PathVariable("id") int id){
        ResponseData response = new ResponseData();
        try{
            if(service.findById(id) != null){
                service.deleteById(id);
                response.setStatus(true);
                response.getMessage().add("Removed Book By Id: "+id);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable("code") String code){
        ResponseData response = new ResponseData();
        try{
            Book book = service.findByCode(code);
            if(book != null){
                response.setPayload(book);
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: "+code);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+code);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> findByTitle(@RequestBody SearchTitle data){
        ResponseData response = new ResponseData();
        try{
            Iterable<Book> books = service.findByTitleLike("%"+data.getTitle()+"%");
            if(books != null ){
                response.setStatus(true);
                response.getMessage().add("Book found");
                response.setPayload(books);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus((false));
                response.getMessage().add("Book Not Found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/price/{start}/to/{end}")
    public ResponseEntity<?> findByPriceRange(@PathVariable("start") double start, @PathVariable("end") double end){
        ResponseData response = new ResponseData();
        try{
            List<Book> book = service.findByPriceBetween(start, end);
            if(book.size() > 0){
                response.setPayload(book);
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: ");
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : ");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //key di request body berdasarkan entity di DTO nya
    @PostMapping("/author")
    public ResponseEntity<?> findByAuthor(@RequestBody SearchAuthor data){
        ResponseData response = new ResponseData();
        try{
            List<Book> books = jrepo.findByAuthorName(data.getAuthor());
            if(books.size() > 0 ){
                response.setStatus(true);
                response.getMessage().add("Book found");
                response.setPayload(books);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus((false));
                response.getMessage().add("Book Not Found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByCategoryId(@PathVariable("id") int id){
        ResponseData response = new ResponseData();
        try{
            List<Book> book = service.findByCategoryId(id);
            if(book != null){
                response.setPayload(book);
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: "+id);
                return ResponseEntity.ok(response);
            }else{
                response.setStatus(false);
                response.getMessage().add("Book Not Found Id : "+id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<?> findAllbyPage(@PathVariable("page") int page, @PathVariable("size") int size){
        ResponseData response = new ResponseData();
        try{
            Pageable pageable = PageRequest.of(page-1, size);
            //List<Book> book = repo.findByCategoryId(id);
            
                response.setPayload(service.findAll(pageable));
                response.setStatus(true);
                response.getMessage().add("Get Book By Id: ");
                return ResponseEntity.ok(response);
           
        }catch(Exception e){
            response.setStatus(false);
            response.getMessage().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
