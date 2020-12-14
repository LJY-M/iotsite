package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.dto.CheckSystemDto;
import com.lot.iotsite.mapper.CheckSystemMapper;
import com.lot.iotsite.service.CheckSystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.List;

@Service
public class CheckSystemServiceImpl implements CheckSystemService {

    @Autowired
    private CheckSystemMapper checkSystemMapper;

    @Override
    public List<CheckSystemDto> getAllCheckSystem() {
        List<CheckSystemDto> checkSystemDtos=new ArrayList<>();
        //查找所有一级体系
        QueryWrapper<CheckSystem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(CheckSystem.FATHER_ID,"0");
        List<CheckSystem> checkSystems=checkSystemMapper.selectList(queryWrapper);
        //查找一级体系对应的二级体系
        BeanUtils.copyProperties(checkSystems,checkSystemDtos);
        for(CheckSystem item:checkSystems){
            CheckSystemDto checkSystemDto=new CheckSystemDto();
            BeanUtils.copyProperties(item,checkSystemDto);
            checkSystemDtos.add(checkSystemDto);
        }
        for(CheckSystemDto item:checkSystemDtos){
            List<CheckSystem> subCheckSystems=getSubCheckSystemById(item.getId());
            List<CheckSystemDto> subCheckSystemDtos=new ArrayList<>();
            for(CheckSystem item1:subCheckSystems){
                CheckSystemDto checkSystemDto=new CheckSystemDto();
                BeanUtils.copyProperties(item1,checkSystemDto);
                subCheckSystemDtos.add(checkSystemDto);
            }
            item.setSubCheckSystems(subCheckSystemDtos);
        }
        return checkSystemDtos;
    }

    @Override
    public List<CheckSystem> getSubCheckSystemById(Long fatherId) {
        QueryWrapper<CheckSystem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(CheckSystem.FATHER_ID,fatherId);
        return checkSystemMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean insertCheckSystem(CheckSystem checkSystem) {
        QueryWrapper<CheckSystem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(CheckSystem.NAME,checkSystem.getName());
        CheckSystem checkSystem1=checkSystemMapper.selectOne(queryWrapper);
        Assert.isNull(checkSystem1,"该检查体系已存在！");
        checkSystemMapper.insert(checkSystem);
        return true;
    }

    @Override
    public Boolean deleteCheckSystem(Long id) {
       CheckSystem checkSystem=getCheckSystemById(id);
       Assert.notNull(checkSystem,"该检查体系不存在！");
       if(0==checkSystem.getFatherId()) {
           List<CheckSystem> subCheckSystems = getSubCheckSystemById(id);
           List<Long> idList=new ArrayList<>();
           for(CheckSystem item:subCheckSystems){
               idList.add(item.getId());
           }
           checkSystemMapper.deleteBatchIds(idList);
       }
       checkSystemMapper.deleteById(id);
       return true;
    }


    @Override
    public CheckSystem getCheckSystemById(Long id) {
       return checkSystemMapper.selectById(id);
    }

    @Override
    public Boolean updateCheckSystem(CheckSystem checkSystem) {
        CheckSystem checkSystem1=getCheckSystemById(checkSystem.getId());
        Assert.notNull(checkSystem,"该检查体系不存在！");
        checkSystem.setFatherId(checkSystem1.getFatherId());
        Assert.isTrue(1==checkSystemMapper.updateById(checkSystem),"更改检查体系信息失败！");
        return true;
    }

    @Override
    public List<CheckSystemDto> getChechSystemByName(String name) {
       List<CheckSystemDto> checkSystemDtos=new ArrayList<>();
       QueryWrapper<CheckSystem> queryWrapper=new QueryWrapper<>();
       queryWrapper.like(CheckSystem.NAME,name);
       List<CheckSystem> checkSystems=checkSystemMapper.selectList(queryWrapper);
       for(CheckSystem item: checkSystems){
           CheckSystemDto checkSystemDto=new CheckSystemDto();
           BeanUtils.copyProperties(item,checkSystemDto);
           checkSystemDtos.add(checkSystemDto);
       }
       return checkSystemDtos;
    }


}
