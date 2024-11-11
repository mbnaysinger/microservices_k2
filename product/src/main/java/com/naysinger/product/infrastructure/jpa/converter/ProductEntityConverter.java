package com.naysinger.product.infrastructure.jpa.converter;

import com.naysinger.product.api.dto.ProductCreateDTO;
import com.naysinger.product.infrastructure.jpa.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityConverter {

    public ProductCreateDTO toDTO(Product product) {
        ProductCreateDTO dto = new ProductCreateDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setModel(product.getModel());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product toEntity(ProductCreateDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setModel(dto.getModel());
        product.setPrice(dto.getPrice());
        return product;
    }
}