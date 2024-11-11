package com.naysinger.product.api.dto;

public class ProductResponseDto {

        private final Long id;
        private final String name;
        private final String model;
        private final Double price;

        public ProductResponseDto(Long id, String name, String model, Double price) {
                this.id = id;
                this.name = name;
                this.model = model;
                this.price = price;
        }

}