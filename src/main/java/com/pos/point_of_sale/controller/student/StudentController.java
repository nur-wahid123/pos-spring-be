package com.pos.point_of_sale.controller.student;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.point_of_sale.controller.student.dto.CreateBatchStudentDto;
import com.pos.point_of_sale.controller.student.dto.CreateStudentDto;
import com.pos.point_of_sale.controller.student.dto.QueryParamStudentDto;
import com.pos.point_of_sale.entities.StudentEntity;
import com.pos.point_of_sale.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Number create(@RequestBody CreateStudentDto data,@RequestHeader("microserviceToken") String token) {
        String tokenEnv = System.getenv("MICROSERVICE_TOKEN");
        if(!tokenEnv.equals(token)) {
            throw new RuntimeException("Invalid microservice token");
        }
        return this.studentService.create(data);
    }

    @PostMapping("/create-batch")
    public Number createBatch(@RequestBody CreateBatchStudentDto data) {
        return this.studentService.createBatch(data);
    }

    @GetMapping("/list")
    public Optional<StudentEntity> getAllStudents(@RequestParam(required = false) QueryParamStudentDto queryParamStudentDto) {
        return this.studentService.getAllStudents(queryParamStudentDto);
    }
}
