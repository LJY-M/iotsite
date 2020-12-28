package com.lot.iotsite.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.domain.Project;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectGradeDto {

    private Project project;

    private Long projectId;

    private String projectName;

    private Long groupId;

    private Integer risk;

    private String status;

    private Long pmId;

    private String description;

    private String constructionUnit;

    private String supervisorUnit;

    private String buildUnit;

    private Long clientId;

    private String groupName;

    private Double grade;
}
