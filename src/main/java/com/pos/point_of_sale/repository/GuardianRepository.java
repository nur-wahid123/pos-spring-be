package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.GuardianEntity;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, Long> {

    Optional<GuardianEntity> findByNik(String nik);
    
}
