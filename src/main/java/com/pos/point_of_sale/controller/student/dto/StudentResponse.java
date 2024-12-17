package com.pos.point_of_sale.controller.student.dto;

import org.springframework.beans.BeanUtils;

import com.pos.point_of_sale.entities.StudentEntity;

public class StudentResponse extends StudentEntity {
    StudentResponse(){}
    StudentResponse(StudentEntity studentEntity){
        BeanUtils.copyProperties(studentEntity, this);
        this.getReligion().setStudents(null);
        this.getBank().setStudents(null);
        this.getKindOfStay().setStudents(null);
        this.getStudentClass().setStudents(null);
        this.getSubDistrict().setStudents(null);
        this.getTransportation().setStudents(null);
    }
}
