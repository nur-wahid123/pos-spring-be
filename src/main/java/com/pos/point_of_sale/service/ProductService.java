package com.pos.point_of_sale.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pos.point_of_sale.controller.product.dto.CreateProductDto;
import com.pos.point_of_sale.entities.ProductEntity;
import com.pos.point_of_sale.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductDetail(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public ProductEntity createProduct(CreateProductDto product) {
        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(product.getName());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            return productRepository.save(productEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product");
        }
    }

}