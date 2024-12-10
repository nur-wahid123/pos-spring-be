package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.TransportationEntity;

@Repository
public interface TransportationRepository extends JpaRepository<TransportationEntity, Long> {
    Optional<TransportationEntity> findByName(String name);
}
