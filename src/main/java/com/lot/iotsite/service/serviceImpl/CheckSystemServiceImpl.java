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
import sun.security.krb5.internal.crypto.CksumType;

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
        List<CheckSystem> checkSystems=checkSystemMapper.selectList(queryWrapper);
        return checkSystems;
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
    public Boolean deleteOneLevelCheckSystem(Long id) {
       CheckSystem checkSystem=getCheckSystemById(id);
       Assert.isNull(checkSystem,"该检查体系不存在！");
       List<CheckSystem> subCheckSystems=getSubCheckSystemById(id);
       checkSystemMapper.deleteBatchIds(subCheckSystems);
       checkSystemMapper.deleteById(id);
       return true;
    }


    @Override
    public Boolean deleteSubCheckSystem(Long id) {
        CheckSystem checkSystem=getCheckSystemById(id);
        Assert.isNull(checkSystem,"该检查体系不存在！");
        checkSystemMapper.deleteById(id);
        return true;
    }

    @Override
    public CheckSystem getCheckSystemById(Long id) {
       return checkSystemMapper.selectById(id);
    }

    @Override
    public Boolean updateCheckSystem(CheckSystem checkSystem) {
       Assert.isTrue(1!=checkSystemMapper.updateById(checkSystem),"更改检查体系信息失败！");
       return true;
    }

}
