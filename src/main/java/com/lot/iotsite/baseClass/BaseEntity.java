package com.lot.iotsite.baseClass;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("基类")
public class BaseEntity {

    @TableField("id")
    @ApiModelProperty("id")
    private Integer id;

    @TableField("create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @TableField("version")
    @Version
    @ApiModelProperty("乐观锁")
    private Integer version;

    public static String ID="id";
    public static String CREATE_TIME="create_time";
    public static String UPDATE_TIME="update_time";
}
