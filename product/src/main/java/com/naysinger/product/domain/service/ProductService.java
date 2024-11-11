package com.naysinger.product.domain.service;

import com.naysinger.product.api.dto.ProductCreateDTO;
import com.naysinger.product.domain.port.ProductPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductPort productPort;

    @Autowired
    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public ProductCreateDTO createProduct(ProductCreateDTO productCreateDTO) {
        return productPort.createProduct(productCreateDTO);
    }

    public ProductCreateDTO getProductById(Long id) {
        return productPort.getProductById(id);
    }

    public List<ProductCreateDTO> getAllProducts() {
        return productPort.getAllProducts();
    }

    public ProductCreateDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        return productPort.updateProduct(id, productCreateDTO);
    }

    public void deleteProduct(Long id) {
        productPort.deleteProduct(id);
    }
}