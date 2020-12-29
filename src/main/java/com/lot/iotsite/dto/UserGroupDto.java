package com.lot.iotsite.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserGroupDto {

    @ApiModelProperty("检查小组id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("检查组id")
    private Long groupId;

    @ApiModelProperty("检查组名称")
    private String groupName;

    @ApiModelProperty("是否是组长")
    private Integer isleader;

}
