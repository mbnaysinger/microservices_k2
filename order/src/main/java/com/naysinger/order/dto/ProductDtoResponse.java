package com.naysinger.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDtoResponse {

        private Long id;
        private String name;
        private String model;
        private Double price;
}