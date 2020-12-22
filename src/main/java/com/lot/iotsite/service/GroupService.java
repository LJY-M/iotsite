package com.lot.iotsite.service;

import com.lot.iotsite.domain.Group;
import com.lot.iotsite.dto.SimpleGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    Group getGroupById(Long id);
    List<SimpleGroupDto> getGroupNames(String groupName);
}
