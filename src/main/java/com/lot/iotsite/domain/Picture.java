package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

@Data
@TableName("t_picture")
public class Picture extends BaseEntity {

    @TableField("check_id")
    private Long checkId;

    @TableField("url")
    private String url;

    public static final String CHECK_ID = "check_id";
    public static final String URL = "url";

}
