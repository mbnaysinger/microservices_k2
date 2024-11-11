package com.naysinger.product.controller.v1;

import com.naysinger.product.dto.ProductDtoRequest;
import com.naysinger.product.dto.ProductDtoResponse;
import com.naysinger.product.entity.Product;
import com.naysinger.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        ProductDtoResponse response = productService.createProduct(productDtoRequest);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public Optional<Product> consultaById(@RequestParam(required = true) Long idProduto) {
        return productService.getProduct(idProduto);
    }
}