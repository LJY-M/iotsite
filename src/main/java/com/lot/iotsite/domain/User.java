package com.lot.iotsite.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lot.iotsite.baseClass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author isHuangXin
 * @since 2020-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel("用户")
public class User  extends BaseEntity {

    @NotBlank(message = "账号不能为空")
    @TableField("account")
    @ApiModelProperty("账号")
    private Integer account;

    @TableField("name")
    @ApiModelProperty("姓名")
    private String name;

    @NotBlank(message = "密码不能为空")
    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("sex")
    @ApiModelProperty("性别")
    private String sex;

    @TableField("user_limit")
    @ApiModelProperty("权限")
    private Integer userLimit;

    @TableField("major")
    @ApiModelProperty("专业")
    private String major;

    @TableField("academic")
    @ApiModelProperty("学历")
    private String academic;

    @TableField("native_place")
    @ApiModelProperty("籍贯")
    private String nativePlace;

    @TableField("address")
    @ApiModelProperty("地址")
    private String address;

    @TableField("telephone")
    @ApiModelProperty("电话")
    private Integer telephone;

    @TableField("job")
    @ApiModelProperty("职位")
    private String job;

    public static final String ACCOUNT="account";
    public static final String PASSWORD="password";
    public static final String NAME="name";
    public static final String SEX="sex";
    public static final String USER_LIMIT="user_limit";
    public static final String MAJOR="major";
    public static final String ACADEMIC="academic";
    public static final String NATIVE_PLACE="native_place";
    public static final String ADDRESS="address";
    public static final String TELEPHONE="telephone";
    public static final String JOB="job";


}
