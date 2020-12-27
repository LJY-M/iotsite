package com.lot.iotsite.service;

import com.lot.iotsite.domain.UserGroup;
import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.dto.UserGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserGroupService {

    // function_1：根据检查小组-用户名查询
    UserGroup getUserGroupById(Long id);
    // function_2：检查所有小组-用户信息
    List<UserGroupDto> getAllUserGroup();
    // function_3：新增小组-用户信息
    Boolean save(UserGroup userGroup);
    // 删除项目检查小组
    Boolean delete(Long groupId, Long userId);
    // 查询小组所有成员
    List<UserDto> getMember(Long id);
    // 查询小组所有是组长的成员
    List<UserDto> getLeader(Long id);
    // 设置组长
    Boolean updateLeader(UserGroup userGroup);
    // 根据组名和用户名一起查询
    UserGroup getUserGroupBygroupIduserId(Long groupId, Long userId);
}
