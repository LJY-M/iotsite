package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("合同实体")
@TableName("t_contract")
public class Contract extends BaseEntity {

    @TableField("client_name")
    @ApiModelProperty("委托人名称")
    private String clientName;

    @TableField("client_Type")
    @ApiModelProperty("委托人类型")
    private String clientType;

    @TableField("creater_id")
    @ApiModelProperty("创建人id")
    private Long createrId;

    @TableField("description")
    @ApiModelProperty("合同描述")
    private String description;

    @TableField("service")
    @ApiModelProperty("服务方案")
    private String service;

    @TableField("client_intention")
    @ApiModelProperty("委托方意向")
    private String clientIntention;

    @TableField("progress")
    @ApiModelProperty("合同进度")
    private Integer progress;

    public static final String CLIENT_NAME="client_name";
    public static final String CLIENT_TYPE="client_type";
    public static final String CREATER_ID="creater_id";
    public static final String DESCRIPTION="description";
    public static final String SERVICE="service";
    public static final String CLIENT_INTENTION="client_intention";
    public static final String PROGRESS="progress";
}
