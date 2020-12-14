package com.lot.iotsite.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectsDto {
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("施工单位")
    private String constructionUnit;

    @ApiModelProperty("建设单位")
    private String buildUnit;

    @ApiModelProperty("监理单位")
    private String supervisorUnit;

    @ApiModelProperty("进度")
    private String status;

    @ApiModelProperty("项目创建时间")
    private LocalDateTime createTime;
}
