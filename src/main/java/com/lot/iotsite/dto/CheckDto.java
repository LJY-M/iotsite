package com.lot.iotsite.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckDto {
    private Long id;
    private String userName;
    private String checkSystems;
    private String risk;
    private LocalDateTime finshDateTime;
}
