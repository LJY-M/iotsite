package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.SimpleUserDto;
import com.lot.iotsite.mapper.UserMapper;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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
    public boolean save(User user){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(User.ACCOUNT,user.getAccount());
        User is_user = userMapper.selectOne(queryWrapper);
        Assert.isNull(is_user,"该用户已存在！");
        userMapper.insert(user);
        return true;
    }

    @Override
    public boolean update(User user){
        User user1 = getUserById(user.getId());
        Assert.notNull(user1,"该y不存在！");
        return true;
    }

    @Override
    public List<SimpleUserDto> getUserByName(String name) {
        List<SimpleUserDto> simpleUserDtos=new ArrayList<>();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(User.NAME,name)
                .orderByAsc(User.NAME);
        List<User> users=userMapper.selectList(queryWrapper);
        for(User user:users){
            SimpleUserDto simpleUserDto=new SimpleUserDto();
            BeanUtils.copyProperties(user,simpleUserDto);
            simpleUserDtos.add(simpleUserDto);
        }
        return simpleUserDtos;
    }
}
