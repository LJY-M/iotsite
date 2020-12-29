package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.SimpleUserDto;
import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.mapper.UserMapper;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

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

//    @Override
//    public User getOne(QueryWrapper<User> account){
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq(User.ID, account.getEntity().getId());
//        return userMapper.selectById(account.getEntity().getId());
//    }

    @Override
    public Boolean save(User user){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(User.ACCOUNT,user.getAccount());
        User is_user = userMapper.selectOne(queryWrapper);
        Assert.isNull(is_user,"该用户已存在！");
        userMapper.insert(user);
        return true;
    }

    //员工信息管理部分
    // function_1: 员工信息显示
    @Override
    public IPage<UserDto> getAllUser(IPage<User> page){
        IPage<UserDto> dtoPage=new Page<>();
        BeanUtils.copyProperties(page,dtoPage);
        List<UserDto> userDtos = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(User.ID);
        page = userMapper.selectPage(page,queryWrapper);
        for (User person:page.getRecords()){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(person, userDto);
            userDtos.add(userDto);
        }
        dtoPage.setRecords(userDtos);
        return dtoPage;
    }

    // function_2: 删除员工
    @Override
    public Boolean delete(Long id){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(User.ID, id);
        Assert.isTrue(1 == userMapper.delete(queryWrapper),"用户删除失败！");
        return true;
    }

    // function_3: 用户信息修改包括权限设置
    @Override
    public Boolean update(User user){
        Assert.isTrue(1 == userMapper.updateById(user), "用户更新失败！");
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

    @Override
    public List<Map> getUserNotAdmin(){
        List<Map> userDtos = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.USER_LIMIT,0);
        queryWrapper.orderByAsc(User.ID);
        List<User> users = userMapper.selectList(queryWrapper);
        for (User person: users){
            Map map = new HashMap();
            map.put("userId",person.getId().toString());
            map.put("userName",person.getName());
            userDtos.add(map);
        }
        return userDtos;
    }
}
