package com.lot.iotsite.queryParam;

import lombok.Data;

@Data
public class UserParam {

    private Long account;
    private String name;
    private String password;
    private String sex;
    private Integer userLimit;
    private String major;
    private String academic;
    private String nativePlace;
    private String address;
    private String telephone;
    private String job;
}
