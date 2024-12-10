package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.ReligionEntity;

@Repository
public interface ReligionRepository extends JpaRepository<ReligionEntity, Long> {

    Optional<ReligionEntity> findByName(String name);
    
}
