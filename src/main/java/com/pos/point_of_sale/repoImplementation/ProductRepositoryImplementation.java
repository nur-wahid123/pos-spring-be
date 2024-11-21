package com.pos.point_of_sale.repoImplementation;

import com.pos.point_of_sale.entities.ProductEntity;

import jakarta.persistence.EntityManager;

public class ProductRepositoryImplementation  {

    private final EntityManager entityManager;
    
    public ProductRepositoryImplementation(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public ProductEntity findByName(String name) {
        ProductEntity data = entityManager.find(ProductEntity.class, name);
        if(data != null){
            return data;
        }
        throw new RuntimeException("Product not found");
    }
    
}
