package com.ecombw.EcommerceBW.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private long id;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private double price;
    @NonNull
    private int stockQuantity;
    private String imageUrl;
}
