package com.pos.point_of_sale.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @Column(unique = true, nullable = false)
    String username;

    @Column(unique = true, nullable = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, name = "phone_number")
    String phoneNumber;
}
