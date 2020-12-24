package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.Group;
import com.lot.iotsite.dto.SimpleGroupDto;
import com.lot.iotsite.mapper.GroupMapper;
import com.lot.iotsite.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Group getGroupById(Long id) {
       return groupMapper.selectById(id);
    }

    @Override
    public List<SimpleGroupDto> getGroupNames() {
        QueryWrapper<Group> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc(Group.NAME);
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
    public Boolean save(Group group){
        QueryWrapper<Group> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Group.NAME, group.getName());
        Group group1 = groupMapper.selectOne(queryWrapper);
        Assert.isNull(group1,"该项目组已存在！");
        groupMapper.insert(group);
        return true;
    }

    // 删除项目检查小组
    @Override
    public Boolean delete(Long id){
        QueryWrapper<Group> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Group.ID, id);
        Assert.isTrue(1 == groupMapper.delete(queryWrapper),"项目小组删除失败！");
        return true;
    }

    // 更新项目检查小组
    @Override
    public Boolean update(Group group){
        Assert.isTrue(1 == groupMapper.updateById(group), "项目更新失败！");
        return true;
    }
}
