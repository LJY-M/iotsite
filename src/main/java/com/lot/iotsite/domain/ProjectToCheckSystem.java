package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

@Data
@TableName("t_pj_checksys")
public class ProjectToCheckSystem extends BaseEntity {

    @TableField("project_id")
    private Long projectId;

    @TableField("father_id")
    private Long fatherId;

    public static final String PROJECT_ID = "project_id";
    public static final String FATHER_ID = "father_id";
}
