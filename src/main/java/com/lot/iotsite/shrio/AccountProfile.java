package com.lot.iotsite.shrio;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {
    private Integer id;

    /**
     * 账户，相当于原user_id
     */
    private Integer account;

    private String name;


    private String sex;

    /**
     * 权限
     */
    private Integer user_limit;

    /**
     * 专业
     */
    private String major;

    /**
     * 学历
     */
    private String academic;

    /**
     * 籍贯
     */
    private String nativeplace;

    /**
     * 住址
     */
    private String address;

    private Integer telephone;

    /**
     * 职位
     */
    private String job;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version;

}
