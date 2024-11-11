package com.naysinger.order.service;

import com.naysinger.order.dto.ProductDtoRequest;
import com.naysinger.order.dto.ProductDtoResponse;
import com.naysinger.order.entity.Product;
import com.naysinger.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDtoResponse createProduct(ProductDtoRequest dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setModel(dto.getModel());
        product.setPrice(dto.getPrice());

        Product savedProduct = productRepository.save(product);

        return new ProductDtoResponse(savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getModel(),
                savedProduct.getPrice());
    }
}