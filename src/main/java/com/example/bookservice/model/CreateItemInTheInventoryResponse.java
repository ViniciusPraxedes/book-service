package com.example.bookservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateItemInTheInventoryResponse {
    private String itemCode;
    private boolean isInStock;
    private Integer quantity;
}
