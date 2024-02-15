package com.example.bookservice.service;


import com.example.bookservice.model.*;
import com.example.bookservice.repository.BookRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final WebClient webClient;

    @Autowired
    public BookService(BookRepository bookRepository, WebClient.Builder webClientBuilder) {
        this.bookRepository = bookRepository;
        this.webClient = webClientBuilder.baseUrl("http://inventory-service.eu-north-1.elasticbeanstalk.com:8040/inventory").build();
    }

    // Creates a new book
    public Book createBook(BookRequest request) {

        //Check if category is valid
        for (int i = 0; i < request.getCategories().size(); i++){
            if (Category.valueOf(request.getCategories().get(i).name()).name().isEmpty()){
                throw new IllegalStateException("Invalid category");
            }
        }


        // Check if book already exists in the database
        if (bookRepository.findByName(request.getName()).isPresent()){
            throw new IllegalStateException("Book already exists");
        }

        String itemCode = UUID.randomUUID().toString();

        // Creates new book
        Book book = Book.builder()
                .name(request.getName())
                .author(request.getAuthor())
                .description(request.getDescription())
                .price(request.getPrice())
                .itemCode(itemCode)
                .numberOfPages(request.getNumberOfPages())
                .imageLink(request.getImageLink())
                .categories(request.getCategories())
                .build();

        //Creates request for inventory service
        CreateItemInTheInventoryRequest ItemRequest = CreateItemInTheInventoryRequest.builder()
                .quantity(0)
                .itemCode(itemCode)
                .build();

        //Makes a request to inventory service
        try {
            webClient.post()
                    .uri("/create")
                    .body(BodyInserters.fromValue(ItemRequest))
                    .exchange()
                    .block();
        } catch (WebClientRequestException e) {
            throw new IllegalStateException("Inventory service is down. Inventory service is required for this endpoint to function properly.");
        }


        // Saves book to the database
        bookRepository.save(book);

        return bookRepository.findByItemCode(book.getItemCode()).get();
    }



    // Get all books in the database
    public List<Book> getAllBooks(){

        // Get all books
        List<Book> books = bookRepository.findAll();

        //Map all the books to BookResponse
        return books;
    }


    // Get book by item code
    public Book getBookByItemCode(String itemCode){

        //Check if book exists
        if (bookRepository.findByItemCode(itemCode).isEmpty()){
            throw new NotFoundException("Book not found");
        }

        return bookRepository.findByItemCode(itemCode).get();
    }


    //Change book by itemCode
    public Book changeBookByItemCode(String itemCode, BookRequest request){

        //Check if categories is valid
        for (int i = 0; i < request.getCategories().size(); i++){
            if (Category.valueOf(request.getCategories().get(i).name()).name().isEmpty()){
                throw new IllegalStateException("Invalid categories");
            }
        }

        //Check if the book exists
        if (bookRepository.findByItemCode(itemCode).isEmpty()){
            throw new NotFoundException("Book not found");
        }

        bookRepository.findByItemCode(itemCode).get().setNumberOfPages(request.getNumberOfPages());
        bookRepository.findByItemCode(itemCode).get().setName(request.getName());
        bookRepository.findByItemCode(itemCode).get().setPrice(request.getPrice());
        bookRepository.findByItemCode(itemCode).get().setAuthor(request.getAuthor());
        bookRepository.findByItemCode(itemCode).get().setDescription(request.getDescription());
        bookRepository.findByItemCode(itemCode).get().setNumberOfPages(request.getNumberOfPages());
        bookRepository.findByItemCode(itemCode).get().setImageLink(request.getImageLink());
        bookRepository.findByItemCode(itemCode).get().setCategories(request.getCategories());
        bookRepository.save(bookRepository.findByItemCode(itemCode).get());

        return bookRepository.findByItemCode(itemCode).get();
    }


    //Delete book
    public void deleteBookByItemCode(String itemCode){

        //Checks if book exists in the database
        if (bookRepository.findByItemCode(itemCode).isEmpty()){
            throw new NotFoundException("Item not found");
        }

        //Makes a request to inventory service
        try {
            webClient.delete()
                    .uri("/{itemCode}", itemCode)
                    .exchange()
                    .block();
        } catch (WebClientRequestException e) {
            throw new IllegalStateException("Inventory service is down. Inventory service is required for this endpoint to function properly.");
        }

        //Deletes book from inventory
        bookRepository.delete(bookRepository.findByItemCode(itemCode).get());
    }



    public List<Category> getAllCategories(){
        return List.of(Category.values());
    }

    public List<Book> getBooksByCategory(Set<Category> categories){


        return bookRepository.findBooksByCategories(categories);
    }

}