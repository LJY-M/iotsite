package com.lot.iotsite.queryParam;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectParam {
    @TableField("name")
    @ApiModelProperty("项目名称")
    private String name;

    @TableField("group_id")
    @ApiModelProperty("小组id")
    private Long groupId;

    @TableField("risk")
    @ApiModelProperty("风险值")
    private Integer risk;

    @TableField("pm_Id")
    @ApiModelProperty("项目经理id")
    private Long pmId;

    @TableField("description")
    @ApiModelProperty("描述")
    private String description;

    @TableField("construction_unit")
    @ApiModelProperty("施工单位")
    private String constructionUnit;

    @TableField("supervisor_unit")
    @ApiModelProperty("监理单位")
    private String supervisorUnit;

    @TableField("build_unit")
    @ApiModelProperty("建设单位")
    private String buildUnit;

    @TableField("client_id")
    @ApiModelProperty("委托方id")
    private Long clientId;
}
