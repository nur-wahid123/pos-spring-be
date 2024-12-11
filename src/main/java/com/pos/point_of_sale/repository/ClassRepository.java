package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity,Long> {
    Optional<ClassEntity> findByName(String name);
}
