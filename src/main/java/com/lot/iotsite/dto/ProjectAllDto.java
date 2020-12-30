package com.lot.iotsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectAllDto {
        private Long projectId;
        private String projectName;
        private Long clientId;
        List<CheckSystemStatusDto> checkSystems;
}
