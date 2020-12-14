package com.lot.iotsite.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractsDto {
    @ApiModelProperty("合同id")
    private Long id;

    @ApiModelProperty("委托人名称")
    private String clientName;

    @ApiModelProperty("委托人类型")
    private String clientType;

    @ApiModelProperty("创建者")
    private String creater;

    @ApiModelProperty("合同进度")
    private String progress;

    @ApiModelProperty("合同创建时间")
    private LocalDateTime createTime;
}
