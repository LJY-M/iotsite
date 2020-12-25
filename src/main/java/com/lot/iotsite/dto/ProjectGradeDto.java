package com.lot.iotsite.dto;

import com.lot.iotsite.domain.Project;
import lombok.Data;

@Data
public class ProjectGradeDto {

    private Project project;

    private Double grade;
}
