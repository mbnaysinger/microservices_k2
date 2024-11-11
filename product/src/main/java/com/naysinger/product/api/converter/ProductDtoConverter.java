package com.naysinger.product.api.converter;

import org.springframework.stereotype.Component;
import com.naysinger.product.api.dto.ProductResponseDto;
import com.naysinger.product.domain.model.ProductModel;

@Component
public class ProductDtoConverter {

    public ProductResponseDto toResponseDto(ProductModel product) {
        if (product == null) {
            return null;
        }

        return new ProductResponseDto(product.getId(), product.getName(), product.getModel(), product.getPrice());
    }
}