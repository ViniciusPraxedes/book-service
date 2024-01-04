package com.example.bookservice.repository;

import com.example.bookservice.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository <Book, Integer> {
    Optional<Book> findByName(String bookName);
    Optional<Book> findByItemCode(String itemCode);

}
