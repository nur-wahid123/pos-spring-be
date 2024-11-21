package com.pos.point_of_sale.controller.product;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.point_of_sale.controller.product.dto.CreateProductDto;
import com.pos.point_of_sale.entities.ProductEntity;
import com.pos.point_of_sale.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public Iterable<ProductEntity> getAllProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/detail/{productId}")
    public Optional<ProductEntity> getProductDetail(@PathVariable Long productId) {
        return productService.getProductDetail(productId);
    }

    @PostMapping("/create")
    public ProductEntity createProduct(@RequestBody CreateProductDto product) {
        return productService.createProduct(product);
    }
}