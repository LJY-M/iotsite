package com.lot.iotsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckSystemDto {
    private Long id;
    private String name;
    private Integer weight;
    private List<CheckSystemDto> subCheckSystems;
}
