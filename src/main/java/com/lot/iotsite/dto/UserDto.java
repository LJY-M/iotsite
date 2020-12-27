package com.lot.iotsite.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private Long account;
    private String name;
 // private String password; 密码不能被查询
    private String sex;
    private Integer userLimit;
    private String major;
    private String academic;
    private String nativePlace;
    private String address;
    private Integer telephone;
    private String job;
}
