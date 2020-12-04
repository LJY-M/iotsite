package com.lot.iotsite.baseClass;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("实体类基类")
public class BaseEntity {

    @TableId(value = "id")
    @ApiModelProperty("id")
    private Integer id;

    @TableLogic(value = "deleted")
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill =FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @TableField(value = "version")
    @Version
    @ApiModelProperty("乐观锁")
    private Integer version;


    public static final String ID="id";
    public static final String CREATE_TIME="create_time";
    public static final String UPDATE_TIME="update_time";
}
