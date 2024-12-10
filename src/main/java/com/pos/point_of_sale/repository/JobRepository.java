package com.pos.point_of_sale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Long> {
    Optional<JobEntity> findByName(String name);
}
