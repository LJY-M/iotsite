package com.lot.iotsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.SimpleUserDto;
import com.lot.iotsite.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService{

    User getUserById(Long id);
    User getUserByAccount(Long account);

    List<SimpleUserDto> getUserByName(String name);
    //User getOne(QueryWrapper<User> account);
    Boolean save(User user);

    // 管理员对人员进行管理
    // function_1: 员工信息显示
    IPage<UserDto> getAllUser(IPage<User> page);

    // function_2: 删除员工
    Boolean delete(Long id);

    // function_3: 用户信息修改包括权限设置
    Boolean update(User user);

    // 获取除去管理员的所有成员
    List<Map> getUserNotAdmin();
}
