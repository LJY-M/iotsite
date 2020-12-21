package com.lot.iotsite.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<User> {

    User getUserById(Long id);
    User getUserByAccount(Long account);
    boolean save(User user);
    boolean update(User user);


    // 管理员对人员进行管理
    // function_1: 员工信息显示
    List<UserDto> getAllUser();

    // function_2: 删除员工
    Boolean delete(Long id);
}
