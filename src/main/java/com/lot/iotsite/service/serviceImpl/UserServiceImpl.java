package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lot.iotsite.domain.User;
import com.lot.iotsite.mapper.UserMapper;
import com.lot.iotsite.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author isHuangXin
 * @since 2020-12-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
