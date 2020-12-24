package com.lot.iotsite.controller;

import com.lot.iotsite.domain.Group;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.domain.UserGroup;
import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.dto.UserGroupDto;
import com.lot.iotsite.mapper.UserGroupMapper;
import com.lot.iotsite.queryParam.UserGroupParam;
import com.lot.iotsite.service.GroupService;
import com.lot.iotsite.service.UserGroupService;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_group")
public class UserGroupController {

    @Autowired
    UserGroupMapper userGroupMapper;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @PostMapping("/check_user_group_by_id/{id}")
    public UserGroupDto getUserGroupById(@PathVariable("id") Long id){
        UserGroup userGroup = userGroupService.getUserGroupById(id);
        User user = userService.getUserById(userGroup.getUserId());
        Group group = groupService.getGroupById(userGroup.getGroupId());
        UserGroupDto userGroupDto = new UserGroupDto();
        // 赋值
        userGroupDto.setId(userGroup.getId());
        userGroupDto.setGroupName(group.getName());
        userGroupDto.setUserName(user.getName());
        userGroupDto.setIsleader(userGroup.getIsLeader());
        return userGroupDto;
    }

    @PostMapping("/check_all_user_group")
    public List<UserGroupDto> getAllUser(){
        return userGroupService.getAllUserGroup();
    }

    @PostMapping("/save_user_group")
    public Boolean save(@SpringQueryMap @RequestBody UserGroupParam userGroupParam){
        org.springframework.util.Assert.notNull(userGroupParam.getGroupId(),"项目检查小组不能为空！");
        org.springframework.util.Assert.notNull(userGroupParam.getUserId(),"用户不能为空！");
        org.springframework.util.Assert.notNull(userGroupParam.getIsleader(),"是否为组长不能为空！");
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupParam, userGroup);
        return userGroupService.save(userGroup);
    }

    @PostMapping("/delete_user/{id}")
    public Boolean deleteUser(@PathVariable("id") Long id){
        return userGroupService.delete(id);
    }

    @PostMapping("/check_group_member/{id}")
    public List<UserDto> getGroupMember(@PathVariable("id") Long id){
        return userGroupService.getMember(id);
    }

    @PostMapping("/check_group_leader/{id}")
    public List<UserDto> getGroupLeader(@PathVariable("id") Long id){
        return userGroupService.getLeader(id);
    }

    @PostMapping("/update_leader/{id}")
    public Boolean updateLeader(@PathVariable("id") Long id,
                                 @SpringQueryMap @RequestBody UserGroupParam userGroupParam){
        /**可以更改的用户信息：
         * private Long userId;
         * private Long groupId;
         * private Long isleader;
         */
        UserGroup userGroup = userGroupService.getUserGroupById(id);
        BeanUtils.copyProperties(userGroupParam,userGroup);
        return userGroupService.updateLeader(userGroup);
    }
}