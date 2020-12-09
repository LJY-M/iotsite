package com.lot.iotsite.queryParam;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContarctParam {
    @ApiModelProperty("委托人名称")
    private String clientName;

    @ApiModelProperty("委托人类型")
    private String clientType;

    @ApiModelProperty("合同描述")
    private String description;

    @ApiModelProperty("服务方案")
    private String service;

    @ApiModelProperty("委托方意向")
    private String clientIntention;
}
