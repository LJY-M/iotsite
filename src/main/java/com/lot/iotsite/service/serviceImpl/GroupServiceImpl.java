package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.domain.Group;
import com.lot.iotsite.domain.UserGroup;
import com.lot.iotsite.dto.SimpleContractDto;
import com.lot.iotsite.dto.SimpleGroupDto;
import com.lot.iotsite.mapper.GroupMapper;
import com.lot.iotsite.mapper.UserGroupMapper;
import com.lot.iotsite.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public Group getGroupById(Long id) {
       return groupMapper.selectById(id);
    }

    @Override
    public List<SimpleGroupDto> getGroupNames(String groupName) {
        QueryWrapper<Group> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(Group.NAME,groupName)
                .orderByAsc(Group.NAME);
        List<Group> groups=groupMapper.selectList(queryWrapper);
        List<SimpleGroupDto> simpleGroupDtos=new ArrayList<>();
        for(Group item:groups){
            SimpleGroupDto simpleGroupDto=new SimpleGroupDto();
            BeanUtils.copyProperties(item,simpleGroupDto);
            simpleGroupDtos.add(simpleGroupDto);
        }
        return simpleGroupDtos;
    }

    @Override
    public List<UserGroup> getGroupByUser(Long userId) {
       QueryWrapper<UserGroup> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq(UserGroup.USER_ID,userId);
        return userGroupMapper.selectList(queryWrapper);
    }
}
