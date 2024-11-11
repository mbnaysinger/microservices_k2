package com.naysinger.product.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDto {

        @NotNull
        private String name;

        @NotNull
        private String model;

        @NotNull
        private Double price;
    }