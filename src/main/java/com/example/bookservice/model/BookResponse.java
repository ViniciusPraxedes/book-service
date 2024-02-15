package com.example.bookservice.model;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class BookResponse {
    private String name;
    private String author;
    @Size(max = 4000)
    private String description;
    private String category;
    private Integer numberOfPages;
    private String itemCode;
    private BigDecimal price;
    @Size(max = 4000)
    private String imageLink;
}
