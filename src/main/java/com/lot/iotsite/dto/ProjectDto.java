package com.lot.iotsite.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lot.iotsite.domain.CheckSystem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectDto {

    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("小组名")
    private String groupName;

    @ApiModelProperty("风险值")
    private Integer risk;

    @ApiModelProperty("进度")
    private String status;

    @ApiModelProperty("项目经理名字")
    private String pmName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("施工单位")
    private String constructionUnit;

    @ApiModelProperty("监理单位")
    private String supervisorUnit;

    @ApiModelProperty("建设单位")
    private String buildUnit;

    @ApiModelProperty("委托方名字")
    private String clientName;

    @ApiModelProperty("项目创建时间")
    private LocalDateTime createTime;

    private List<CheckSystemDto> checkSystems;
}
