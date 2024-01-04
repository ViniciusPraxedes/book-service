package com.example.bookservice.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;


@Data
@Builder
public class BookRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Number of pages cannot be null")
    @Positive(message = "Number of pages must be a positive number")
    private Integer numberOfPages;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.0")
    private BigDecimal price;
}
