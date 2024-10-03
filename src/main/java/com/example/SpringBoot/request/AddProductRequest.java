package com.example.SpringBoot.request;

import com.example.SpringBoot.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;
    private Category category;

}
