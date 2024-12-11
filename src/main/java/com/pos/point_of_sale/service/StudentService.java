package com.pos.point_of_sale.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.pos.point_of_sale.controller.student.dto.CreateBatchStudentDto;
import com.pos.point_of_sale.controller.student.dto.CreateStudentDto;
import com.pos.point_of_sale.controller.student.dto.QueryParamStudentDto;
import com.pos.point_of_sale.entities.BankEntity;
import com.pos.point_of_sale.entities.ClassEntity;
import com.pos.point_of_sale.entities.EducationEntity;
import com.pos.point_of_sale.entities.GuardianEntity;
import com.pos.point_of_sale.entities.IncomeEntity;
import com.pos.point_of_sale.entities.JobEntity;
import com.pos.point_of_sale.entities.KindOfStayEntity;
import com.pos.point_of_sale.entities.ParentsEntity;
import com.pos.point_of_sale.entities.ReligionEntity;
import com.pos.point_of_sale.entities.StudentEntity;
import com.pos.point_of_sale.entities.SubdistrictEntity;
import com.pos.point_of_sale.entities.TransportationEntity;
import com.pos.point_of_sale.enums.ClassTypeEnum;
import com.pos.point_of_sale.enums.GenderEnum;
import com.pos.point_of_sale.repository.BankRepository;
import com.pos.point_of_sale.repository.ClassRepository;
import com.pos.point_of_sale.repository.EducationRepository;
import com.pos.point_of_sale.repository.GuardianRepository;
import com.pos.point_of_sale.repository.IncomeRepository;
import com.pos.point_of_sale.repository.JobRepository;
import com.pos.point_of_sale.repository.KindOfStayRepository;
import com.pos.point_of_sale.repository.ParentsRepository;
import com.pos.point_of_sale.repository.ReligionRepository;
import com.pos.point_of_sale.repository.StudentRepository;
import com.pos.point_of_sale.repository.SubdistrictRepository;
import com.pos.point_of_sale.repository.TransportationRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final KindOfStayRepository kindOfStayRepository;

    private final ReligionRepository religionRepository;

    private final SubdistrictRepository subdistrictRepository;

    private final TransportationRepository transportationRepository;

    private final ParentsRepository parentsRepository;

    private final GuardianRepository guardianRepository;

    private final EducationRepository educationRepository;

    private final JobRepository jobRepository;

    private final ClassRepository classRepository;

    private final IncomeRepository incomeRepository;

    private final BankRepository bankRepository;

    public StudentService(BankRepository bankRepository, EducationRepository educationRepository,
            IncomeRepository incomeRepository,
            JobRepository jobRepository,
            ClassRepository classRepository,
            ParentsRepository parentsRepository,
            GuardianRepository guardianRepository,
            TransportationRepository transportationRepository,
            KindOfStayRepository kindOfStayRepository,
            StudentRepository studentRepository, ReligionRepository religionRepository,
            SubdistrictRepository subdistrictRepository) {
        this.bankRepository = bankRepository;
        this.jobRepository = jobRepository;
        this.educationRepository = educationRepository;
        this.incomeRepository = incomeRepository;
        this.parentsRepository = parentsRepository;
        this.guardianRepository = guardianRepository;
        this.transportationRepository = transportationRepository;
        this.kindOfStayRepository = kindOfStayRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.subdistrictRepository = subdistrictRepository;
        this.religionRepository = religionRepository;
    }

    public Optional<StudentEntity> getAllStudents(QueryParamStudentDto queryParamStudentDto) {
        Pageable pageable = PageRequest.of(0, 1); // Fetch 10 records, starting at page 0
        Optional<StudentEntity> students = studentRepository.findById((long) 1);
        if (students != null) {
            Optional<StudentEntity> limitedStudents = students;
            return limitedStudents;
        }
        return students;
    }

    @Transactional
    public Number createBatch(CreateBatchStudentDto data) {
        List<CreateStudentDto> students = data.getItems();
        List<StudentEntity> entities = new ArrayList<>();
        try {
            for (CreateStudentDto createStudentDto : students) {
                StudentEntity entity = this.studentRepository
                        .findByStudentNationalId(createStudentDto.getStudentNationalId()).orElseGet(() -> {
                            return new StudentEntity();
                        });
                entity.setName(createStudentDto.getName());
                entity.setStudentSchoolId(createStudentDto.getStudentSchoolId());
                entity.setGender(GenderEnum.valueOf(createStudentDto.getGender().toUpperCase().charAt(0) + ""));
                entity.setStudentNationalId(createStudentDto.getStudentNationalId());
                if (createStudentDto.getPlaceOfBirth() != null) {
                    entity.setPlaceOfBirth(createStudentDto.getPlaceOfBirth());
                }
                if (createStudentDto.getDateOfBirth() != null) {
                    entity.setDateOfBirth(LocalDateTime.parse(createStudentDto.getDateOfBirth() + "T00:00:00"));
                }
                if (createStudentDto.getNik() != null) {
                    entity.setNik(createStudentDto.getNik());
                }
                if (createStudentDto.getReligion() != null) {
                    String religionName = createStudentDto.getReligion();
                    Optional<ReligionEntity> religion = religionRepository.findByName(religionName);
                    if (religion.isPresent()) {
                        entity.setReligion(religion.get());
                    } else {
                        ReligionEntity religionEntity = new ReligionEntity();
                        religionEntity.setName(religionName);
                        this.religionRepository.save(religionEntity);
                        entity.setReligion(religionEntity);
                    }
                }
                entity.setAddress(Optional.ofNullable(createStudentDto.getAddress()).orElse(entity.getAddress()));
                entity.setHamlet(Optional.ofNullable(createStudentDto.getHamlet()).orElse(entity.getHamlet()));
                entity.setWard(Optional.ofNullable(createStudentDto.getWard()).orElse(entity.getWard()));

                Optional.ofNullable(createStudentDto.getSubDistrict()).ifPresent(subDistrict -> {
                    Optional<SubdistrictEntity> subdistrict = this.subdistrictRepository.findByName(subDistrict);
                    if (subdistrict.isPresent()) {
                        entity.setSubDistrict(subdistrict.get());
                    } else {
                        SubdistrictEntity subdistrictEntity = new SubdistrictEntity();
                        subdistrictEntity.setName(subDistrict);
                        this.subdistrictRepository.save(subdistrictEntity);
                        entity.setSubDistrict(subdistrictEntity);
                    }
                });

                entity.setPostalCode(
                        Optional.ofNullable(createStudentDto.getPostalCode()).orElse(entity.getPostalCode()));

                Optional.ofNullable(createStudentDto.getKindOfStay()).ifPresent(kindOfStay -> {
                    KindOfStayEntity kindOfStayEntity = this.kindOfStayRepository.findByName(kindOfStay)
                            .orElseGet(() -> {
                                KindOfStayEntity newKindOfStay = new KindOfStayEntity();
                                newKindOfStay.setName(kindOfStay);
                                this.kindOfStayRepository.save(newKindOfStay);
                                return newKindOfStay;
                            });
                    entity.setKindOfStay(kindOfStayEntity);
                });

                Optional.ofNullable(createStudentDto.getTransportation()).ifPresent(transportation -> {
                    TransportationEntity transportationEntity = this.transportationRepository.findByName(transportation)
                            .orElseGet(() -> {
                                TransportationEntity newTransportation = new TransportationEntity();
                                newTransportation.setName(transportation);
                                this.transportationRepository.save(newTransportation);
                                return newTransportation;
                            });
                    entity.setTransportation(transportationEntity);
                });

                entity.setTelephone(Optional.ofNullable(createStudentDto.getTelephone()).orElse(entity.getTelephone()));
                entity.setPhoneNumber(
                        Optional.ofNullable(createStudentDto.getPhoneNumber()).orElse(entity.getPhoneNumber()));
                entity.setEmail(Optional.ofNullable(createStudentDto.getEmail()).orElse(entity.getEmail()));
                entity.setSkhun(Optional.ofNullable(createStudentDto.getSkhun()).orElse(entity.getSkhun()));
                entity.setIsKps(Optional.ofNullable(createStudentDto.getIsKps()).orElse(entity.getIsKps()));
                entity.setKpsId(Optional.ofNullable(createStudentDto.getKpsId()).orElse(entity.getKpsId()));
                if (createStudentDto.getFather() != null && createStudentDto.getFather().getNik() != null) {
                    entity.setFather(Optional.ofNullable(createStudentDto.getFather()).map(father -> {
                        ParentsEntity fatherEntity = this.parentsRepository
                                .findByNik(father.getNik()).orElseGet(() -> {
                                    ParentsEntity newFather = new ParentsEntity();
                                    newFather.setName(Optional.ofNullable(father.getName()).orElse(""));
                                    newFather.setNik(Optional.ofNullable(father.getNik()).orElse(""));
                                    newFather.setYearOfBirth(father.getYearOfBirth());
                                    if (father.getEducation() != null) {
                                        EducationEntity educationEntity = this.educationRepository
                                                .findByName(father.getEducation()).orElseGet(() -> {
                                                    EducationEntity newEducationEntity = new EducationEntity();
                                                    newEducationEntity.setName(father.getEducation());
                                                    this.educationRepository.save(newEducationEntity);
                                                    return newEducationEntity;
                                                });
                                        newFather.setEducation(educationEntity);
                                    }
                                    if (father.getJob() != null) {
                                        JobEntity jobEntity = this.jobRepository.findByName(father.getJob())
                                                .orElseGet(() -> {
                                                    JobEntity newJobEntity = new JobEntity();
                                                    newJobEntity.setName(father.getJob());
                                                    this.jobRepository.save(newJobEntity);
                                                    return newJobEntity;
                                                });
                                        newFather.setJob(jobEntity);
                                    }
                                    if (father.getIncome() != null) {
                                        IncomeEntity incomeEntity = this.incomeRepository.findByName(father.getIncome())
                                                .orElseGet(() -> {
                                                    IncomeEntity newIncomeEntity = new IncomeEntity();
                                                    newIncomeEntity.setName(father.getIncome());
                                                    this.incomeRepository.save(newIncomeEntity);
                                                    return newIncomeEntity;
                                                });
                                        newFather.setIncome(incomeEntity);
                                    }
                                    this.parentsRepository.save(newFather);
                                    return newFather;
                                });
                        return fatherEntity;
                    }).orElse(null));
                } else {
                    entity.setFather(null);
                }
                if (createStudentDto.getMother() != null && createStudentDto.getMother().getNik() != null) {

                    entity.setMother(Optional.ofNullable(createStudentDto.getMother()).map(mother -> {
                        ParentsEntity motherEntity = this.parentsRepository
                                .findByNik(mother.getNik()).orElseGet(() -> {
                                    ParentsEntity newMother = new ParentsEntity();
                                    newMother.setName(Optional.ofNullable(mother.getName()).orElse(""));
                                    newMother.setNik(Optional.ofNullable(mother.getNik()).orElse(""));
                                    newMother.setYearOfBirth(mother.getYearOfBirth());
                                    if (mother.getEducation() != null) {
                                        EducationEntity educationEntity = this.educationRepository
                                                .findByName(mother.getEducation()).orElseGet(() -> {
                                                    EducationEntity newEducationEntity = new EducationEntity();
                                                    newEducationEntity.setName(mother.getEducation());
                                                    this.educationRepository.save(newEducationEntity);
                                                    return newEducationEntity;
                                                });
                                        newMother.setEducation(educationEntity);
                                    }
                                    if (mother.getJob() != null) {
                                        JobEntity jobEntity = this.jobRepository.findByName(mother.getJob())
                                                .orElseGet(() -> {
                                                    JobEntity newJobEntity = new JobEntity();
                                                    newJobEntity.setName(mother.getJob());
                                                    this.jobRepository.save(newJobEntity);
                                                    return newJobEntity;
                                                });
                                        newMother.setJob(jobEntity);
                                    }
                                    if (mother.getIncome() != null) {
                                        IncomeEntity incomeEntity = this.incomeRepository.findByName(mother.getIncome())
                                                .orElseGet(() -> {
                                                    IncomeEntity newIncomeEntity = new IncomeEntity();
                                                    newIncomeEntity.setName(mother.getIncome());
                                                    this.incomeRepository.save(newIncomeEntity);
                                                    return newIncomeEntity;
                                                });
                                        newMother.setIncome(incomeEntity);
                                    }
                                    this.parentsRepository.save(newMother);
                                    return newMother;
                                });
                        return motherEntity;
                    }).orElse(null));
                } else {
                    entity.setMother(null);
                }
                if (createStudentDto.getGuardian() != null && createStudentDto.getGuardian().getNik() != null) {
                    entity.setGuardian(Optional.ofNullable(createStudentDto.getGuardian()).map(guardian -> {
                        GuardianEntity guardianEntity = this.guardianRepository
                                .findByNik(guardian.getNik()).orElseGet(() -> {
                                    GuardianEntity newGuardian = new GuardianEntity();
                                    newGuardian.setName(Optional.ofNullable(guardian.getName()).orElse(""));
                                    newGuardian.setNik(Optional.ofNullable(guardian.getNik()).orElse(""));
                                    newGuardian.setYearOfBirth(guardian.getYearOfBirth());
                                    if (guardian.getEducation() != null) {
                                        EducationEntity educationEntity = this.educationRepository
                                                .findByName(guardian.getEducation()).orElseGet(() -> {
                                                    EducationEntity newEducationEntity = new EducationEntity();
                                                    newEducationEntity.setName(guardian.getEducation());
                                                    this.educationRepository.save(newEducationEntity);
                                                    return newEducationEntity;
                                                });
                                        newGuardian.setEducation(educationEntity);
                                    }
                                    if (guardian.getJob() != null) {
                                        JobEntity jobEntity = this.jobRepository.findByName(guardian.getJob())
                                                .orElseGet(() -> {
                                                    JobEntity newJobEntity = new JobEntity();
                                                    newJobEntity.setName(guardian.getJob());
                                                    this.jobRepository.save(newJobEntity);
                                                    return newJobEntity;
                                                });
                                        newGuardian.setJob(jobEntity);
                                    }
                                    if (guardian.getIncome() != null) {
                                        IncomeEntity incomeEntity = this.incomeRepository
                                                .findByName(guardian.getIncome())
                                                .orElseGet(() -> {
                                                    IncomeEntity newIncomeEntity = new IncomeEntity();
                                                    newIncomeEntity.setName(guardian.getIncome());
                                                    this.incomeRepository.save(newIncomeEntity);
                                                    return newIncomeEntity;
                                                });
                                        newGuardian.setIncome(incomeEntity);
                                    }
                                    this.guardianRepository.save(newGuardian);
                                    return newGuardian;
                                });
                        return guardianEntity;
                    }).orElse(null));
                } else {
                    entity.setGuardian(null);
                }
                if (createStudentDto.getNationalTestNumber() != null) {
                    entity.setNationalTestNumber(createStudentDto.getNationalTestNumber());
                }
                if (createStudentDto.getGraduationSertificateNumber() != null) {
                    entity.setGraduationSertificateNumber(createStudentDto.getGraduationSertificateNumber());
                }
                if (createStudentDto.getIsKip() != null) {
                    entity.setIsKip(createStudentDto.getIsKip());
                }
                if (createStudentDto.getKipId() != null) {
                    entity.setKipId(createStudentDto.getKipId());
                }
                if (createStudentDto.getIsNameInKip() != null) {
                    entity.setNameInKip(createStudentDto.getIsNameInKip());
                }
                if (createStudentDto.getKksId() != null) {
                    entity.setKksId(createStudentDto.getKksId());
                }
                if (createStudentDto.getBirthCertificateRegistrationId() != null) {
                    entity.setBirthCertificateRegistrationId(createStudentDto.getBirthCertificateRegistrationId());
                }
                if (createStudentDto.getBank() != null) {
                    BankEntity bankEntity = this.bankRepository.findByName(createStudentDto.getBank()).orElseGet(() -> {
                        BankEntity newBankEntity = new BankEntity();
                        newBankEntity.setName(createStudentDto.getBank());
                        this.bankRepository.save(newBankEntity);
                        return newBankEntity;
                    });
                    entity.setBank(bankEntity);
                }
                if (createStudentDto.getStudyGroup() != null) {
                    ClassEntity classEntity = this.classRepository.findByName(createStudentDto.getStudyGroup())
                            .orElseGet(() -> {
                                ClassEntity newclassEntity = new ClassEntity();
                                newclassEntity.setName(createStudentDto.getStudyGroup());
                                newclassEntity.setClassType(ClassTypeEnum.XI);
                                this.classRepository.save(newclassEntity);
                                return newclassEntity;
                            });
                    entity.setStudentClass(classEntity);
                }
                if (createStudentDto.getBankAccountNumber() != null) {
                    entity.setBankAccountNumber(createStudentDto.getBankAccountNumber());
                }
                if (createStudentDto.getBankAccountName() != null) {
                    entity.setBankAccountName(createStudentDto.getBankAccountName());
                }
                if (createStudentDto.getIsPipWorthy() != null) {
                    entity.setIsPipWorthy(createStudentDto.getIsPipWorthy());
                }
                if (createStudentDto.getReasonPipWorthy() != null) {
                    entity.setReasonPipWorthy(createStudentDto.getReasonPipWorthy());
                }
                if (createStudentDto.getDisability() != null) {
                    entity.setDisability(createStudentDto.getDisability());
                }
                if (createStudentDto.getJuniorSchoolName() != null) {
                    entity.setJuniorSchoolName(createStudentDto.getJuniorSchoolName());
                }
                if (createStudentDto.getChildOrder() != null) {
                    entity.setChildOrder(createStudentDto.getChildOrder());
                }
                if (createStudentDto.getLongitude() != null) {
                    entity.setLongitude(createStudentDto.getLongitude());
                }
                if (createStudentDto.getLatitude() != null) {
                    entity.setLatitude(createStudentDto.getLatitude());
                }
                if (createStudentDto.getFamilyCardId() != null) {
                    entity.setFamilyCardId(createStudentDto.getFamilyCardId());
                }
                if (createStudentDto.getWeight() != null) {
                    entity.setWeight(createStudentDto.getWeight());
                }
                if (createStudentDto.getHeight() != null) {
                    entity.setHeight(createStudentDto.getHeight());
                }
                if (createStudentDto.getHeadCircumference() != null) {
                    entity.setHeadCircumference(createStudentDto.getHeadCircumference());
                }
                if (createStudentDto.getNumberOfSiblings() != null) {
                    entity.setNumberOfSiblings(createStudentDto.getNumberOfSiblings());
                }
                if (createStudentDto.getDistanceFromSchool() != null) {
                    entity.setDistanceFromSchool(createStudentDto.getDistanceFromSchool());
                }
                // entity.setMetadata(new Object());
                entities.add(entity);
            }
            System.out.println("entities.size() = " + entities.size());
            this.studentRepository.saveAll(entities);
            return entities.size();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public Number create(CreateStudentDto createStudentDto) {
        List<StudentEntity> entities = new ArrayList<>();
        try {
            StudentEntity entity = this.studentRepository
                    .findByStudentNationalId(createStudentDto.getStudentNationalId()).orElseGet(() -> {
                        return new StudentEntity();
                    });
            entity.setName(createStudentDto.getName());
            entity.setStudentSchoolId(createStudentDto.getStudentSchoolId());
            entity.setGender(GenderEnum.fromValue(createStudentDto.getGender()));
            entity.setStudentNationalId(createStudentDto.getStudentNationalId());
            if (createStudentDto.getPlaceOfBirth() != null) {
                entity.setPlaceOfBirth(createStudentDto.getPlaceOfBirth());
            }
            if (createStudentDto.getDateOfBirth() != null) {
                entity.setDateOfBirth(LocalDateTime.parse(createStudentDto.getDateOfBirth() + "T00:00:00"));
            }
            if (createStudentDto.getNik() != null) {
                entity.setNik(createStudentDto.getNik());
            }
            if (createStudentDto.getReligion() != null) {
                String religionName = createStudentDto.getReligion();
                Optional<ReligionEntity> religion = religionRepository.findByName(religionName);
                if (religion.isPresent()) {
                    entity.setReligion(religion.get());
                } else {
                    ReligionEntity religionEntity = new ReligionEntity();
                    religionEntity.setName(religionName);
                    this.religionRepository.save(religionEntity);
                    entity.setReligion(religionEntity);
                }
            }
            entity.setAddress(Optional.ofNullable(createStudentDto.getAddress()).orElse(null));
            entity.setHamlet(Optional.ofNullable(createStudentDto.getHamlet()).orElse(null));
            entity.setWard(Optional.ofNullable(createStudentDto.getWard()).orElse(null));

            Optional.ofNullable(createStudentDto.getSubDistrict()).ifPresent(subDistrict -> {
                Optional<SubdistrictEntity> subdistrict = this.subdistrictRepository.findByName(subDistrict);
                if (subdistrict.isPresent()) {
                    entity.setSubDistrict(subdistrict.get());
                } else {
                    SubdistrictEntity subdistrictEntity = new SubdistrictEntity();
                    subdistrictEntity.setName(subDistrict);
                    this.subdistrictRepository.save(subdistrictEntity);
                    entity.setSubDistrict(subdistrictEntity);
                }
            });

            entity.setPostalCode(
                    Optional.ofNullable(createStudentDto.getPostalCode()).orElse(null));

            Optional.ofNullable(createStudentDto.getKindOfStay()).ifPresent(kindOfStay -> {
                KindOfStayEntity kindOfStayEntity = this.kindOfStayRepository.findByName(kindOfStay)
                        .orElseGet(() -> {
                            KindOfStayEntity newKindOfStay = new KindOfStayEntity();
                            newKindOfStay.setName(kindOfStay);
                            this.kindOfStayRepository.save(newKindOfStay);
                            return newKindOfStay;
                        });
                entity.setKindOfStay(kindOfStayEntity);
            });

            Optional.ofNullable(createStudentDto.getTransportation()).ifPresent(transportation -> {
                TransportationEntity transportationEntity = this.transportationRepository.findByName(transportation)
                        .orElseGet(() -> {
                            TransportationEntity newTransportation = new TransportationEntity();
                            newTransportation.setName(transportation);
                            this.transportationRepository.save(newTransportation);
                            return newTransportation;
                        });
                entity.setTransportation(transportationEntity);
            });

            entity.setTelephone(Optional.ofNullable(createStudentDto.getTelephone()).orElse(null));
            entity.setPhoneNumber(
                    Optional.ofNullable(createStudentDto.getPhoneNumber()).orElse(null));
            entity.setEmail(Optional.ofNullable(createStudentDto.getEmail()).orElse(null));
            entity.setSkhun(Optional.ofNullable(createStudentDto.getSkhun()).orElse(null));
            entity.setIsKps(Optional.ofNullable(createStudentDto.getIsKps()).orElse(null));
            entity.setKpsId(Optional.ofNullable(createStudentDto.getKpsId()).orElse(null));
            if (createStudentDto.getFather().getNik() != null) {
                entity.setFather(Optional.ofNullable(createStudentDto.getFather()).map(father -> {
                    ParentsEntity fatherEntity = this.parentsRepository
                            .findByNik(father.getNik()).orElseGet(() -> {
                                ParentsEntity newFather = new ParentsEntity();
                                newFather.setName(Optional.ofNullable(father.getName()).orElse(""));
                                newFather.setNik(Optional.ofNullable(father.getNik()).orElse(""));
                                newFather.setYearOfBirth(father.getYearOfBirth());
                                EducationEntity educationEntity = this.educationRepository
                                        .findByName(father.getEducation()).orElseGet(() -> {
                                            EducationEntity newEducationEntity = new EducationEntity();
                                            newEducationEntity.setName(father.getEducation());
                                            this.educationRepository.save(newEducationEntity);
                                            return newEducationEntity;
                                        });
                                newFather.setEducation(educationEntity);
                                JobEntity jobEntity = this.jobRepository.findByName(father.getJob())
                                        .orElseGet(() -> {
                                            JobEntity newJobEntity = new JobEntity();
                                            newJobEntity.setName(father.getJob());
                                            this.jobRepository.save(newJobEntity);
                                            return newJobEntity;
                                        });
                                newFather.setJob(jobEntity);
                                IncomeEntity incomeEntity = this.incomeRepository.findByName(father.getIncome())
                                        .orElseGet(() -> {
                                            IncomeEntity newIncomeEntity = new IncomeEntity();
                                            newIncomeEntity.setName(father.getIncome());
                                            this.incomeRepository.save(newIncomeEntity);
                                            return newIncomeEntity;
                                        });
                                newFather.setIncome(incomeEntity);
                                this.parentsRepository.save(newFather);
                                return newFather;
                            });
                    return fatherEntity;
                }).orElse(null));
            } else {
                entity.setFather(null);
            }
            if (createStudentDto.getMother().getNik() != null) {
                entity.setMother(Optional.ofNullable(createStudentDto.getMother()).map(mother -> {
                    ParentsEntity motherEntity = this.parentsRepository
                            .findByNik(mother.getNik()).orElseGet(() -> {
                                ParentsEntity newMother = new ParentsEntity();
                                newMother.setName(Optional.ofNullable(mother.getName()).orElse(""));
                                newMother.setNik(Optional.ofNullable(mother.getNik()).orElse(""));
                                newMother.setYearOfBirth(mother.getYearOfBirth());
                                EducationEntity educationEntity = this.educationRepository
                                        .findByName(mother.getEducation()).orElseGet(() -> {
                                            EducationEntity newEducationEntity = new EducationEntity();
                                            newEducationEntity.setName(mother.getEducation());
                                            this.educationRepository.save(newEducationEntity);
                                            return newEducationEntity;
                                        });
                                newMother.setEducation(educationEntity);
                                JobEntity jobEntity = this.jobRepository.findByName(mother.getJob())
                                        .orElseGet(() -> {
                                            JobEntity newJobEntity = new JobEntity();
                                            newJobEntity.setName(mother.getJob());
                                            this.jobRepository.save(newJobEntity);
                                            return newJobEntity;
                                        });
                                newMother.setJob(jobEntity);
                                IncomeEntity incomeEntity = this.incomeRepository.findByName(mother.getIncome())
                                        .orElseGet(() -> {
                                            IncomeEntity newIncomeEntity = new IncomeEntity();
                                            newIncomeEntity.setName(mother.getIncome());
                                            this.incomeRepository.save(newIncomeEntity);
                                            return newIncomeEntity;
                                        });
                                newMother.setIncome(incomeEntity);
                                this.parentsRepository.save(newMother);
                                return newMother;
                            });
                    return motherEntity;
                }).orElse(null));
            } else {
                entity.setMother(null);
            }
            if (createStudentDto.getGuardian().getNik() != null) {
                entity.setGuardian(Optional.ofNullable(createStudentDto.getGuardian()).map(guardian -> {
                    GuardianEntity guardianEntity = this.guardianRepository
                            .findByNik(guardian.getNik()).orElseGet(() -> {
                                GuardianEntity newGuardian = new GuardianEntity();
                                newGuardian.setName(Optional.ofNullable(guardian.getName()).orElse(""));
                                newGuardian.setNik(Optional.ofNullable(guardian.getNik()).orElse(""));
                                newGuardian.setYearOfBirth(guardian.getYearOfBirth());
                                EducationEntity educationEntity = this.educationRepository
                                        .findByName(guardian.getEducation()).orElseGet(() -> {
                                            EducationEntity newEducationEntity = new EducationEntity();
                                            newEducationEntity.setName(guardian.getEducation());
                                            this.educationRepository.save(newEducationEntity);
                                            return newEducationEntity;
                                        });
                                newGuardian.setEducation(educationEntity);
                                JobEntity jobEntity = this.jobRepository.findByName(guardian.getJob())
                                        .orElseGet(() -> {
                                            JobEntity newJobEntity = new JobEntity();
                                            newJobEntity.setName(guardian.getJob());
                                            this.jobRepository.save(newJobEntity);
                                            return newJobEntity;
                                        });
                                newGuardian.setJob(jobEntity);
                                IncomeEntity incomeEntity = this.incomeRepository.findByName(guardian.getIncome())
                                        .orElseGet(() -> {
                                            IncomeEntity newIncomeEntity = new IncomeEntity();
                                            newIncomeEntity.setName(guardian.getIncome());
                                            this.incomeRepository.save(newIncomeEntity);
                                            return newIncomeEntity;
                                        });
                                newGuardian.setIncome(incomeEntity);
                                this.guardianRepository.save(newGuardian);
                                return newGuardian;
                            });
                    return guardianEntity;
                }).orElse(null));
            } else {
                entity.setGuardian(null);
            }
            if (createStudentDto.getNationalTestNumber() != null) {
                entity.setNationalTestNumber(createStudentDto.getNationalTestNumber());
            }
            if (createStudentDto.getGraduationSertificateNumber() != null) {
                entity.setGraduationSertificateNumber(createStudentDto.getGraduationSertificateNumber());
            }
            if (createStudentDto.getIsKip() != null) {
                entity.setIsKip(createStudentDto.getIsKip());
            }
            if (createStudentDto.getKipId() != null) {
                entity.setKipId(createStudentDto.getKipId());
            }
            if (createStudentDto.getIsNameInKip() != null) {
                entity.setNameInKip(createStudentDto.getIsNameInKip());
            }
            if (createStudentDto.getKksId() != null) {
                entity.setKksId(createStudentDto.getKksId());
            }
            if (createStudentDto.getBirthCertificateRegistrationId() != null) {
                entity.setBirthCertificateRegistrationId(createStudentDto.getBirthCertificateRegistrationId());
            }
            if (createStudentDto.getBank() != null) {
                BankEntity bankEntity = this.bankRepository.findByName(createStudentDto.getBank()).orElseGet(() -> {
                    BankEntity newBankEntity = new BankEntity();
                    newBankEntity.setName(createStudentDto.getBank());
                    this.bankRepository.save(newBankEntity);
                    return newBankEntity;
                });
                entity.setBank(bankEntity);
            }
            if (createStudentDto.getStudyGroup() != null) {
                ClassEntity classEntity = this.classRepository.findByName(createStudentDto.getStudyGroup())
                        .orElseGet(() -> {
                            ClassEntity newclassEntity = new ClassEntity();
                            newclassEntity.setName(createStudentDto.getStudyGroup());
                            newclassEntity.setClassType(ClassTypeEnum.XI);
                            this.classRepository.save(newclassEntity);
                            return newclassEntity;
                        });
                entity.setStudentClass(classEntity);
            }
            if (createStudentDto.getBankAccountNumber() != null) {
                entity.setBankAccountNumber(createStudentDto.getBankAccountNumber());
            }
            if (createStudentDto.getBankAccountName() != null) {
                entity.setBankAccountName(createStudentDto.getBankAccountName());
            }
            if (createStudentDto.getIsPipWorthy() != null) {
                entity.setIsPipWorthy(createStudentDto.getIsPipWorthy());
            }
            if (createStudentDto.getReasonPipWorthy() != null) {
                entity.setReasonPipWorthy(createStudentDto.getReasonPipWorthy());
            }
            if (createStudentDto.getDisability() != null) {
                entity.setDisability(createStudentDto.getDisability());
            }
            if (createStudentDto.getJuniorSchoolName() != null) {
                entity.setJuniorSchoolName(createStudentDto.getJuniorSchoolName());
            }
            if (createStudentDto.getChildOrder() != null) {
                entity.setChildOrder(createStudentDto.getChildOrder());
            }
            if (createStudentDto.getLongitude() != null) {
                entity.setLongitude(createStudentDto.getLongitude());
            }
            if (createStudentDto.getLatitude() != null) {
                entity.setLatitude(createStudentDto.getLatitude());
            }
            if (createStudentDto.getFamilyCardId() != null) {
                entity.setFamilyCardId(createStudentDto.getFamilyCardId());
            }
            if (createStudentDto.getWeight() != null) {
                entity.setWeight(createStudentDto.getWeight());
            }
            if (createStudentDto.getHeight() != null) {
                entity.setHeight(createStudentDto.getHeight());
            }
            if (createStudentDto.getHeadCircumference() != null) {
                entity.setHeadCircumference(createStudentDto.getHeadCircumference());
            }
            if (createStudentDto.getNumberOfSiblings() != null) {
                entity.setNumberOfSiblings(createStudentDto.getNumberOfSiblings());
            }
            if (createStudentDto.getDistanceFromSchool() != null) {
                entity.setDistanceFromSchool(createStudentDto.getDistanceFromSchool());
            }
            // entity.setMetadata(Objects.requireNonNullElseGet(entity.getMetadata(), () ->
            // new Object() {
            // public String createdBy = "SYSTEM";
            // public String createdDate = LocalDateTime.now().toString();
            // }));
            entities.add(entity);
            System.out.println("entities.size() = " + entities.size());
            this.studentRepository.saveAll(entities);
            return entities.size();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

}
