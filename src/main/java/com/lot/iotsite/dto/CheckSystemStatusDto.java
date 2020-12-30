package com.lot.iotsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckSystemStatusDto {
    private Long id;
    private String name;
    private Integer weight;
    private Integer examState;
    private Integer passState;
    private List<CheckSystemStatusDto> subCheckSystems;
}
