package com.pos.point_of_sale.entities;

import java.time.LocalDateTime;

import com.pos.point_of_sale.enums.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "student")
@EqualsAndHashCode(callSuper = true)
public class StudentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true, name = "student_school_id")
  private String studentSchoolId;

  @Column(nullable = false)
  private GenderEnum gender;

  @Column(nullable = false, unique = true, name = "student_national_id")
  private String studentNationalId;

  @Column(nullable = true, name = "place_of_birth")
  private String placeOfBirth;

  @Column(nullable = true, name = "date_of_birth")
  private LocalDateTime dateOfBirth;

  @Column(nullable = false, name = "is_active")
  private Boolean isActive=true;

  @Column(nullable = true, unique = true)
  private String nik;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "religion_id", nullable = true)
  private ReligionEntity religion;

  @Column(nullable = true)
  private String address;

  @Column(nullable = true)
  private String hamlet;

  @Column(nullable = true)
  private String ward;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "sub_district_id", nullable = true)
  private SubdistrictEntity subDistrict;

  @Column(nullable = true, name = "postal_code")
  private Long postalCode;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "kind_of_stay_id", nullable = true)
  private KindOfStayEntity kindOfStay;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "transportation_id", nullable = true)
  private TransportationEntity transportation;

  @Column(nullable = true)
  private String telephone;

  @Column(nullable = true, name = "phone_number")
  private String phoneNumber;

  @Column(nullable = true)
  private String email;

  @Column(nullable = true)
  private String skhun;

  @Column(nullable = false, name = "is_kps")
  private Boolean isKps = false;

  @Column(nullable = true, name = "kps_id")
  private String kpsId;

  @ManyToOne(fetch=FetchType.LAZY)
  private ParentsEntity father;

  @ManyToOne(fetch=FetchType.LAZY)
  private ParentsEntity mother;

  @ManyToOne(fetch=FetchType.LAZY)
  private ParentsEntity guardian;

  @Column(nullable = true, name = "national_test_number")
  private String nationalTestNumber;

  @Column(nullable = true, name = "graduation_sertificate_number")
  private String graduationSertificateNumber;

  @Column(nullable = false, name = "is_kip")
  private Boolean isKip = false;

  @Column(nullable = true, name = "kip_id")
  private String kipId;

  @Column(nullable = true, name = "is_name_in_kip")
  private boolean isNameInKip;

  @Column(nullable = true, name = "kks_id")
  private String kksId;

  @Column(nullable = true, name = "birth_certificate_registration_id")
  private String birthCertificateRegistrationId;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "bank_id", nullable = true)
  private BankEntity bank;

  @Column(nullable = true, name = "bank_account_number")
  private String bankAccountNumber;

  @Column(nullable = true, name = "bank_account_name")
  private String bankAccountName;

  @Column(nullable = false, name = "is_pip_worthy")
  private Boolean isPipWorthy = false;

  @Column(nullable = true, name = "reason_pip_worthy")
  private String reasonPipWorthy;

  @Column(nullable = true)
  private String disability;

  @Column(nullable = true, name = "junior_school_name")
  private String juniorSchoolName;

  @Column(nullable = true, name = "child_order")
  private Long childOrder;

  @Column(nullable = true)
  private String latitude;

  @Column(nullable = true)
  private String longitude;

  @Column(nullable = true, name = "ily_card_id")
  private String familyCardId;

  @Column(nullable = true)
  private Long weight;

  @Column(nullable = true)
  private Long height;

  @Column(nullable = true, name = "head_circumference")
  private Long headCircumference;

  @Column(nullable = true, name = "number_of_siblings")
  private Long numberOfSiblings;

  @Column(nullable = true, name = "distance_from_school")
  private Long distanceFromSchool;
}