package com.lot.iotsite.queryParam;

import lombok.Data;

@Data
public class UserGroupParam {

    private Long userId;
    private Long groupId;
    private Long isleader;
}
