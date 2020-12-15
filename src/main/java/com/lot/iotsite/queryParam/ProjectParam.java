package com.lot.iotsite.queryParam;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectParam {
    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("小组id")
    private Long groupId;

    @ApiModelProperty("风险值")
    private Integer risk;

    @ApiModelProperty("项目经理id")
    private Long pmId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("施工单位")
    private String constructionUnit;

    @ApiModelProperty("监理单位")
    private String supervisorUnit;

    @ApiModelProperty("建设单位")
    private String buildUnit;

    @ApiModelProperty("委托方id")
    private Long clientId;

    private List<Long> checkSystems;
}
