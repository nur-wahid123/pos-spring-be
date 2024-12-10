package com.pos.point_of_sale.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "parents")
@EqualsAndHashCode(callSuper = true)
public class ParentsEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "year_of_birth", nullable = true)
    private String yearOfBirth;

    @Column(name = "nik", unique = false, nullable = true)
    private String nik;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "education_id", nullable = true)
    private EducationEntity education;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = true)
    private JobEntity job;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "income_id", nullable = true)
    private IncomeEntity income;

}
