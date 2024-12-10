package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.SubdistrictEntity;

@Repository
public interface SubdistrictRepository extends JpaRepository<SubdistrictEntity, Long> {
    Optional<SubdistrictEntity> findByName(String name);
}
