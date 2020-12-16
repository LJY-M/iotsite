package com.lot.iotsite.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lot.iotsite.domain.User;


public interface UserService extends IService<User> {

    User getUserById(Long id);

    User getUserByAccount(Long account);

    boolean save(User user);

    boolean update(User user);
}
