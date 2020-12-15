package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_group")
@ApiModel("")
public class Group {

    @NotBlank(message = "组名不能为空")
    @TableField("name")
    @ApiModelProperty("组名")
    private Long name;

    public static final String NAME="name";
}
