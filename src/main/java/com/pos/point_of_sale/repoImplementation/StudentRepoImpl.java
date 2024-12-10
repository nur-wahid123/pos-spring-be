package com.pos.point_of_sale.repoImplementation;

import org.springframework.stereotype.Repository;

import com.pos.point_of_sale.entities.StudentEntity;
import com.pos.point_of_sale.repository.CustomStudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class StudentRepoImpl implements CustomStudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Iterable<StudentEntity> searchStudents(String search) {
        String query = "SELECT s FROM StudentEntity s WHERE lower(s.name) LIKE lower(:search) OR s.studentNationalId LIKE :search";
        return entityManager.createQuery(query, StudentEntity.class)
                            .setMaxResults(5)
                            .setParameter("search", "%" + search + "%")
                            .getResultList();
    }
    
}
