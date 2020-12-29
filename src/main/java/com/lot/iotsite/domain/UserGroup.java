package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

@Data
@TableName("t_usergroup")
public class UserGroup extends BaseEntity {
    @TableField("user_id")
    private Long userId;
    @TableField("group_id")
    private Long groupId;
    @TableField("isleader")
    private Integer isleader;

    public static final String USER_ID = "user_id";
    public static final String GROUP_ID = "group_id";
    public static final String IS_LEADER = "isleader";
}
