package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.BookRequest;
import com.example.bookservice.model.Category;
import com.example.bookservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public Book createBook(@Valid @RequestBody BookRequest request) {
        return bookService.createBook(request);
    }


    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }


    @GetMapping("/{itemCode}")
    public Book getBookByItemCode(@PathVariable String itemCode){
        return bookService.getBookByItemCode(itemCode);
    }

    @GetMapping("/all/categories")
    public List<Category> getAllCategories(){
        return bookService.getAllCategories();
    }


    @GetMapping("/books/{categories}")
    public List<Book> getAllCategories(@PathVariable Set<Category> categories){
        System.out.println(categories);
        return bookService.getBooksByCategory(categories);
    }

}
