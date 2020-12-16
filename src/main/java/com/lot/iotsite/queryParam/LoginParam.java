package com.lot.iotsite.queryParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginParam {

    @ApiModelProperty("用户账号")
    private Long account;

    @ApiModelProperty("用户密码")
    private String password;
}
