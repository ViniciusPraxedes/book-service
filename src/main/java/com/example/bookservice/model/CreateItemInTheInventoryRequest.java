package com.example.bookservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateItemInTheInventoryRequest {
    @NotBlank(message = "Item code cannot be blank")
    private String itemCode;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be equal to or greater than 0")
    private Integer quantity;
}
