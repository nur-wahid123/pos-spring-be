package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.IncomeEntity;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {
    Optional<IncomeEntity> findByName(String name);
}
