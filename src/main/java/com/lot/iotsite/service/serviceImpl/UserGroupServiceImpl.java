package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.Group;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.domain.UserGroup;
import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.dto.UserGroupDto;
import com.lot.iotsite.mapper.UserGroupMapper;
import com.lot.iotsite.service.GroupService;
import com.lot.iotsite.service.UserGroupService;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService{

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Override
    public UserGroup getUserGroupById(Long id){
        return userGroupMapper.selectById(id);
    }

    @Override
    public List<UserGroupDto> getAllUserGroup(){
        List<UserGroupDto> userGroupDtos = new ArrayList<>();
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(UserGroup.ID);
        List<UserGroup> userGroups = userGroupMapper.selectList(queryWrapper);
        for (UserGroup userGroup: userGroups){
            UserGroupDto userGroupDto = new UserGroupDto();
            // 赋值
            User user = userService.getUserById(userGroup.getUserId());
            Group group = groupService.getGroupById(userGroup.getGroupId());
            userGroupDto.setId(userGroup.getId());
            userGroupDto.setGroupName(group.getName());
            userGroupDto.setUserName(user.getName());
            userGroupDto.setIsleader(userGroup.getIsLeader());
            //BeanUtils.copyProperties(userGroup, userGroupDto);
            userGroupDtos.add(userGroupDto);
        }
        return userGroupDtos;
    }

    @Override
    public Boolean save(UserGroup userGroup){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.IS_LEADER, userGroup.getIsLeader());
        List<UserGroup> list = new ArrayList<>();
        list = userGroupMapper.selectList(queryWrapper);
        if (list.size()>3){
            Assert.state(false,"组长人数不能超过3人");
        }
        userGroupMapper.insert(userGroup);
        return true;
    }

    @Override
    public Boolean delete(Long groupId, Long userId){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.GROUP_ID, groupId);
        queryWrapper.eq(UserGroup.USER_ID, userId);
        Assert.isTrue(1 == userGroupMapper.delete(queryWrapper),"用户删除失败！");
        return true;
    }

    @Override
    public List<UserGroupDto> getMember(Long id){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.GROUP_ID, id);
        queryWrapper.orderByAsc(UserGroup.USER_ID);
        // 在UserGroup表中查询到记录相应小组的所有成员记录
        List<UserGroup> userGroups = userGroupMapper.selectList(queryWrapper);
        List<UserGroupDto> userGroupDtos = new ArrayList<>();
        for(UserGroup member : userGroups){
            User user = userService.getUserById(member.getUserId());
            Group group = groupService.getGroupById(member.getGroupId());
            UserGroupDto userGroupDto = new UserGroupDto();
            //BeanUtils.copyProperties(user, userGroupDto);
            // ===========
            userGroupDto.setId(member.getId());
            userGroupDto.setId(member.getUserId());
            userGroupDto.setUserName(user.getName());
            userGroupDto.setGroupId(member.getGroupId());
            userGroupDto.setGroupName(group.getName());
            userGroupDto.setIsleader(member.getIsLeader());
            // ============
            userGroupDtos.add(userGroupDto);
        }
        return userGroupDtos;
    }

    @Override
    public List<UserDto> getLeader(Long id){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.GROUP_ID, id);
        queryWrapper.eq(UserGroup.IS_LEADER,1);
        queryWrapper.orderByAsc(UserGroup.USER_ID);
        // 在UserGroup表中查询到记录相应小组的所有成员记录
        List<UserGroup> userGroups = userGroupMapper.selectList(queryWrapper);
        List<UserDto> userDtos = new ArrayList<>();
        for(UserGroup member : userGroups){
            User user = userService.getUserById(member.getUserId());
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public Boolean updateLeader(UserGroup userGroup){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.GROUP_ID,userGroup.getGroupId())
                     .eq(UserGroup.IS_LEADER,1);
        List<UserGroup> userGroups=userGroupMapper.selectList(queryWrapper);
        Assert.isTrue(0==userGroup.getIsLeader()||userGroups.size()<3,"小组最多只能设置三个组长！");
        Assert.isTrue(1 == userGroupMapper.updateById(userGroup), "组长设置失败！");
        return true;
    }


    @Override
    public UserGroup getUserGroupBygroupIduserId(Long groupId, Long userId){
        QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(UserGroup.GROUP_ID, groupId);
        queryWrapper.eq(UserGroup.USER_ID, userId);
        return userGroupMapper.selectOne(queryWrapper);
    }
}
