package com.pos.point_of_sale.controller.product.dto;

import lombok.Data;

@Data
public class CreateProductDto {
    String name;
    String description;
    Double price;
}
