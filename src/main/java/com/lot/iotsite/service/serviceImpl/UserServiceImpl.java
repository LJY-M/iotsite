package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.mapper.UserMapper;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public boolean save(User user){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(User.ACCOUNT,user.getAccount());
        User is_user = userMapper.selectOne(queryWrapper);
        Assert.isNull(is_user,"该用户已存在！");
        userMapper.insert(user);
        return true;
    }

//    @Override
//    public Boolean updateCheckSystem(CheckSystem checkSystem) {
//        CheckSystem checkSystem1=getCheckSystemById(checkSystem.getId());
//        Assert.notNull(checkSystem,"该检查体系不存在！");
//        checkSystem.setFatherId(checkSystem1.getFatherId());
//        Assert.isTrue(1==checkSystemMapper.updateById(checkSystem),"更改检查体系信息失败！");
//        return true;
//    }
    @Override
    public boolean update(User user){
        User user1 = getUserById(user.getId());
        Assert.notNull(user1,"该y不存在！");
    }
}
