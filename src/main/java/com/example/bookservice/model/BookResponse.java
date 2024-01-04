package com.example.bookservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class BookResponse {
    private String name;
    private String author;
    private String description;
    private Integer numberOfPages;
    private String itemCode;
    private BigDecimal price;
}
