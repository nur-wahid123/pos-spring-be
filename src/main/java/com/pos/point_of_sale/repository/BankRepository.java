package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.BankEntity;

@Repository
public interface BankRepository extends JpaRepository<BankEntity,Long> {
    Optional<BankEntity> findByName(String name);
}
