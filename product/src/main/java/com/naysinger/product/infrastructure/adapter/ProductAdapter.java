package com.naysinger.product.infrastructure.adapter;

import com.naysinger.product.api.converter.ProductDTOConverter;
import com.naysinger.product.api.dto.ProductCreateDTO;
import com.naysinger.product.domain.model.ProductModel;
import com.naysinger.product.domain.port.ProductPort;
import com.naysinger.product.infrastructure.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAdapter implements ProductPort {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOConverter productConverter;

    @Override
    public ProductCreateDTO createProduct(ProductCreateDTO productCreateDTO) {
        ProductModel productModel = productConverter.toModel(productCreateDTO);
        productModel = productRepository.save(productModel);
        return productConverter.toDto(productModel);
    }

    @Override
    public ProductCreateDTO getProductById(Long id) {
        ProductModel productModel = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productConverter.toDto(productModel);
    }

    @Override
    public List<ProductCreateDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productConverter::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductCreateDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        ProductModel productModel = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productModel.setName(productCreateDTO.getName());
        productModel.setModel(productCreateDTO.getModel());
        productModel.setPrice(productCreateDTO.getPrice());
        productModel = productRepository.save(productModel);
        return productConverter.toDTO(productModel);


        @Override
        public Industry update(Industry industry) {
            var entity = converter.toEntity(industry);
            return converter.toModel(repository.save(entity));
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}