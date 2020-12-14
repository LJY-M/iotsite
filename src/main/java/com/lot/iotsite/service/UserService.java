package com.lot.iotsite.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lot.iotsite.domain.User;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author isHuangXin
 * @since 2020-12-04
 */
public interface UserService extends IService<User> {
    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 通过account获取用户信息
     * @param account
     * @return
     */
    User getUserByAccount(Long account);
}
