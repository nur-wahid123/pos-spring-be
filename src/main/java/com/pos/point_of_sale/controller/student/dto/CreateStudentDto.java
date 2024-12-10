package com.pos.point_of_sale.controller.student.dto;

import com.pos.point_of_sale.enums.GenderEnum;

import lombok.Data;

@Data
public class CreateStudentDto {
         private String name;
         private String studentSchoolId;
         private String gender;
         private String studentNationalId;
         private String placeOfBirth;
         private String dateOfBirth;
         private String nik;
         private String religion;
         private String address;
         private String hamlet;
         private String ward;
         private String subDistrict;
         private Long postalCode;
         private String kindOfStay;
         private String transportation;
         private String telephone;
         private String phoneNumber;
         private String email;
         private String skhun;
         private Boolean isKps;
         private String kpsId;
         private Father father;
         private Mother mother;
         private Guardian guardian;
         private String studyGroup;
         private String nationalTestNumber;
         private String graduationSertificateNumber;
         private Boolean isKip;
         private String kipId;
         private Boolean isNameInKip;
         private String kksId;
         private String birthCertificateRegistrationId;
         private String bank;
         private String bankAccountNumber;
         private String bankAccountName;
         private Boolean isPipWorthy = false;
         private String reasonPipWorthy;
         private String disability;
         private String juniorSchoolName;
         private Long childOrder = (long) 1;
         private String latitude;
         private String longitude;
         private String familyCardId;
         private Long weight;
         private Long height;
         private Long headCircumference;
         private Long numberOfSiblings;
         private Long distanceFromSchool;
}
