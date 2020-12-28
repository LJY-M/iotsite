package com.lot.iotsite.dto;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.CheckResult;
import com.lot.iotsite.domain.Group;
import com.lot.iotsite.domain.UserGroup;
import lombok.Data;

import java.util.List;

@Data
public class UserGroupCheckDto {

    private UserGroup userGroup;

    private Group group;

    private List<CheckResult> checkResultList;
}
