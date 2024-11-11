package com.naysinger.product.api.rest;

import com.naysinger.product.api.dto.ProductCreateDTO;
import com.naysinger.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductCreateDTO> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        ProductCreateDTO createdProduct = productService.createProduct(productCreateDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCreateDTO> getProductById(@PathVariable Long id) {
        ProductCreateDTO productCreateDTO = productService.getProductById(id);
        return ResponseEntity.ok(productCreateDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductCreateDTO>> getAllProducts() {
        List<ProductCreateDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCreateDTO> updateProduct(@PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO) {
        ProductCreateDTO updatedProduct = productService.updateProduct(id, productCreateDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}