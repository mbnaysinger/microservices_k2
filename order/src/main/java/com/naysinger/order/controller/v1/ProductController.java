package com.naysinger.order.controller.v1;

import com.naysinger.order.dto.ProductDtoRequest;
import com.naysinger.order.dto.ProductDtoResponse;
import com.naysinger.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        ProductDtoResponse response = productService.createProduct(productDtoRequest);
        return ResponseEntity.ok(response);
    }
}