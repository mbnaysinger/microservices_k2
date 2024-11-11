package com.naysinger.product.domain.port;

import com.naysinger.product.api.dto.ProductCreateDTO;
import java.util.List;

public interface ProductPort {
    ProductCreateDTO createProduct(ProductCreateDTO productCreateDTO);
    ProductCreateDTO getProductById(Long id);
    List<ProductCreateDTO> getAllProducts();
    ProductCreateDTO updateProduct(Long id, ProductCreateDTO productCreateDTO);
    void deleteProduct(Long id);
}