package com.naysinger.product.dto;

import lombok.Data;

@Data
public class ProductDtoRequest {

        private String name;
        private String model;
        private Double price;
}