package com.naysinger.product.service;

import com.naysinger.product.dto.ProductDtoRequest;
import com.naysinger.product.dto.ProductDtoResponse;
import com.naysinger.product.entity.Product;
import com.naysinger.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

}