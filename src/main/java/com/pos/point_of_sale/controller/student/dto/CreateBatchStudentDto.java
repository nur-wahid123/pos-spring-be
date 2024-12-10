package com.pos.point_of_sale.controller.student.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateBatchStudentDto {
    private List<CreateStudentDto> items;
}
