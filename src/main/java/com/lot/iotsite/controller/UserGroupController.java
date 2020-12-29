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
import org.springframework.validation.annotation.Validated;
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

    // 根据usergroup_id查询usergroup表中的记录
    @GetMapping("/check_user_group_by_id/{id}")
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

    // 查询所有usergroup
    @GetMapping("/check_all_user_group")
    public List<UserGroupDto> getAllUserGroup(){
        return userGroupService.getAllUserGroup();
    }

    // 新增保存一个usergroup
    @PostMapping("/save_user_group")
    public Boolean save(@SpringQueryMap @RequestParam UserGroupParam userGroupParam){
        org.springframework.util.Assert.notNull(userGroupParam.getGroupId(),"项目检查小组不能为空！");
        org.springframework.util.Assert.notNull(userGroupParam.getUserId(),"用户不能为空！");
        org.springframework.util.Assert.notNull(userGroupParam.getIsleader(),"是否为组长不能为空！");
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupParam, userGroup);
        return userGroupService.save(userGroup);
    }

    // 删除一个usergroup
    @DeleteMapping("/delete_user")
    public Boolean deleteUserGroup(@Validated @RequestParam(value = "groupId") Long groupId,
                              @RequestParam(value = "userId") Long userId){
        return userGroupService.delete(userId, groupId);
    }

    @GetMapping("/check_group_member/{id}")
    public List<UserGroupDto> getGroupMember(@PathVariable("id") Long id){
        return userGroupService.getMember(id);
    }

    @GetMapping("/check_group_leader/{id}")
    public List<UserDto> getGroupLeader(@PathVariable("id") Long id){
        return userGroupService.getLeader(id);
    }

    // 更新一个usergroup的成员信息
    @PutMapping("/update_leader")
    public Boolean updateLeader(@RequestParam(value = "groupId") Long groupId,
                                @RequestParam(value = "userId") Long userId,
                                 @RequestParam(value = "role")Integer role){
        /**可以更改的用户信息：
         * private Long userId;
         * private Long groupId;
         * private Long isleader;
         */
        UserGroup userGroup=userGroupService.getUserGroupBygroupIduserId(groupId,userId);
        userGroup.setIsLeader(role);
        return userGroupService.updateLeader(userGroup);

    }
}