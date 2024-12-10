package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.KindOfStayEntity;

@Repository
public interface KindOfStayRepository extends JpaRepository<KindOfStayEntity,Long> {
    Optional<KindOfStayEntity> findByName(String name);
}
