package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_check")
public class Check extends BaseEntity {

    @TableField("project_id")
    private int projectId;

    @TableField("group_id")
    private int groupId;

    @TableField("user_id")
    private int userId;

    @TableField("checksys_id")
    private int checkSystemId;

    @TableField("grade")
    private int grade;

    @TableField("description")
    private String description;

    @TableField("exam_state")
    private int examState;

    @TableField("pass_state")
    private int passState;

    @TableField("finish_time")
    private LocalDateTime finishDateTime;
}
