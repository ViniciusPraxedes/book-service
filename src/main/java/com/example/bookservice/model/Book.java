package com.example.bookservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String author;
    @Size(max = 4000)
    private String description;
    private String itemCode;
    private Integer numberOfPages;
    private BigDecimal price;
    @Size(max = 4000)
    private String imageLink;
}
