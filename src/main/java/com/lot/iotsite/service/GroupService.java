package com.lot.iotsite.service;

import com.lot.iotsite.domain.Group;
import com.lot.iotsite.dto.SimpleGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    Group getGroupById(Long id);
    // 检查小组信息
    List<SimpleGroupDto> getGroupNames();
    // 新增检查小组信息
    Boolean save(Group group);
    // 删除项目检查小组
    Boolean delete(Long id);
    // 更新项目检查小组
    Boolean update(Group group);
}
