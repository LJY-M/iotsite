package com.lot.iotsite.shrio;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {

    private Long id;

    private Long account;

    private String name;

    private String sex;

    private Integer userLimit;

    private String major;

    private String academic;

    private String nativePlace;

    private String address;

    private Integer telephone;

    private String job;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version;

}
