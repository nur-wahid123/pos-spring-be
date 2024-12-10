package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.ParentsEntity;

@Repository
public interface ParentsRepository extends JpaRepository<ParentsEntity, Long> {

    Optional<ParentsEntity> findByNik(String nik);
    
}
