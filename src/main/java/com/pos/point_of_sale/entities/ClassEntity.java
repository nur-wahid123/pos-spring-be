package com.pos.point_of_sale.entities;

import java.util.List;

import com.pos.point_of_sale.enums.ClassTypeEnum;
import com.pos.point_of_sale.enums.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "class")
@EqualsAndHashCode(callSuper = true)
public class ClassEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(nullable = true, name = "homeroom_teacher")
    private String homeroomTeacher;

    @OneToMany(mappedBy = "studentClass")
    private List<StudentEntity> students;

    @Column(nullable = false, name = "class_type")
    @Enumerated(EnumType.STRING)
    private ClassTypeEnum classType = ClassTypeEnum.X;
    
}
