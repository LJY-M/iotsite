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
    private Long projectId;

    @TableField("group_id")
    private Long groupId;

    @TableField("user_id")
    private Long userId;

    @TableField("checksys_id")
    private Integer checkSystemId;

    @TableField("grade")
    private Integer grade;

    @TableField("description")
    private String description;

    @TableField("exam_state")
    private Integer examState;

    @TableField("pass_state")
    private Integer passState;

    @TableField("finish_time")
    private LocalDateTime finishDateTime;

    public static final String PROJECT_ID = "project_id";
    public static final String GROUP_ID = "group_id";
    public static final String USER_ID = "user_id";
    public static final String CHECK_SYSTEM_ID = "checksys_id";
    public static final String GRADE = "grade";
    public static final String DESCRIPTION = "description";
    public static final String EXAM_STATE = "exam_state";
    public static final String PASS_STATE = "pass_state";
    public static final String FINISH_DATA_TIME = "finish_time";
}
