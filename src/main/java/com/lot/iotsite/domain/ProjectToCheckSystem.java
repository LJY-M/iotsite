package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

@Data
@TableName("t_pj_checksys")
public class ProjectToCheckSystem extends BaseEntity {

    @TableField("project_id")
    private int projectId;

    @TableField("father_id")
    private int fatherId;
}
