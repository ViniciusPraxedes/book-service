package com.example.bookservice.service;


import com.example.bookservice.model.*;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
            throw new RuntimeException("Book not found");
        }

        return bookRepository.findByItemCode(itemCode).get();
    }

    public List<Category> getAllCategories(){
        return List.of(Category.values());
    }

    public List<Book> getBooksByCategory(Set<Category> categories){
        return bookRepository.findBooksByCategories(categories);
    }

}