package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_project")
@ApiModel("项目实体")
public class Project extends BaseEntity {

    @TableField("name")
    @ApiModelProperty("项目名称")
    private String name;

    @TableField("group_id")
    @ApiModelProperty("小组id")
    private Long groupId;

    @TableField("risk")
    @ApiModelProperty("风险值")
    private Integer risk;

    @TableField("progress")
    @ApiModelProperty("进度")
    private Integer progress;

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
    private String clientId;

    public static final String NAME="name";
    public static final String GROUP_ID="group_id";
    public static final String RISK="risk";
    public static final String PROGRESS="progress";
    public static final String PM_ID="pm_id";
    public static final String DESCRIPTION="description";
    public static final String CONSTRUCTION_UNIT="construction_unit";
    public static final String SUPERVISOR_UNIT="supervisor_unit";
    public static final String BUILD_UNIT="build_unit";
    public static final String CLIENT_ID="client_id";

}
