package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.SimpleGroupDto;
import com.lot.iotsite.dto.SimpleUserDto;
import com.lot.iotsite.mapper.UserMapper;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
       return userMapper.selectById(id);
    }

    @Override
    public User getUserByAccount(Long account) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(User.ACCOUNT,account);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SimpleUserDto> getUserNames() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc(User.NAME);
        List<User> users=userMapper.selectList(queryWrapper);
        List<SimpleUserDto> simpleUserDtos=new ArrayList<>();
        for(User user:users){
            SimpleUserDto simpleUserDto=new SimpleUserDto();
            BeanUtils.copyProperties(user,simpleUserDto);
            simpleUserDtos.add(simpleUserDto);
        }
        return simpleUserDtos;
    }
}
