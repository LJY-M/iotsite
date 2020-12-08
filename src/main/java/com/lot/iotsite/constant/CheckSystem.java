package com.lot.iotsite.constant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import lombok.Data;

//  This table contains the first level inspection system and the second level inspection system
//        "Id" is the only distinction of this system
//        "Name" is the name of the system
//        "Weight" is the weight of the system
@Data
@TableName("t_checksys")
public class CheckSystem extends BaseEntity {

    @TableField("name")
    private String name;

    @TableField("weight")
    private int weight;

//    If it is null, the system is a first-level system
//    If not null, the system is a secondary system,
//    and the value corresponds to its parent system ID
    @TableField("father_id")
    private int fatherId;
}
