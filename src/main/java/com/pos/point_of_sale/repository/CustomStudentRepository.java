package com.pos.point_of_sale.repository;

import com.pos.point_of_sale.entities.StudentEntity;

public interface CustomStudentRepository {

    Iterable<StudentEntity> searchStudents(String search);

}
