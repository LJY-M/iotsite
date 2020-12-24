package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_usergroup")
public class UserGroup {
    @TableField("user_id")
    private Long userId;
    @TableField("group_id")
    private Long groupId;
    @TableField("isleader")
    private Integer isLeader;

    public static final String USER_ID="user_id";
    public static final String GROUP_ID="group_id";
    public static final String IS_LEADER="isleader";
}
