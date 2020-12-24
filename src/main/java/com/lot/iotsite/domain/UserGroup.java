package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_usergroup")
@ApiModel("检查小组")
public class UserGroup extends BaseEntity {

    @TableField("user_id")
    @ApiModelProperty("用户id")
    private Long userId;

    @TableField("group_id")
    @ApiModelProperty("小组id")
    private Long groupId;

    @TableField("isleader")
    @ApiModelProperty("小组id")
    private Long isleader;

    public static final String USERID="user_id";
    public static final String GROUPID="group_id";
    public static final String ISLEADER="isleader";
}
