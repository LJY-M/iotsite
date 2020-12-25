package com.lot.iotsite.dto;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.UserGroup;
import lombok.Data;

import java.util.List;

@Data
public class UserGroupCheckDto {

    private UserGroup userGroup;

    private List<Check> checkList;
}
