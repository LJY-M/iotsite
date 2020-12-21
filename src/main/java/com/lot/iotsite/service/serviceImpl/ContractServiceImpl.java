package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.dto.ContractDto;
import com.lot.iotsite.dto.ContractsDto;
import com.lot.iotsite.dto.SimpleContractDto;
import com.lot.iotsite.mapper.ContractMapper;
import com.lot.iotsite.service.ContractService;
import com.lot.iotsite.service.ProjectService;
import com.lot.iotsite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Boolean addContract(Contract contract) {
       Assert.isTrue(1==contractMapper.insert(contract),"创建合同失败！");
       return true;
    }

    @Override
    public Boolean updateContract(Contract contract) {
        Assert.isTrue(1==contractMapper.updateById(contract),"修改合同信息失败！");
        return true;
    }

    @Override
    public Boolean deleteContractById(Long id) {
        Assert.isTrue(1==contractMapper.deleteById(id),"删除合同失败！");
        //删除关联项目
        projectService.deleteProjectByClienId(id);
        return true;
    }

    @Override
    public IPage<ContractsDto> getAllContractsDto(String name, String type, String startTime, String endTime,Integer status, IPage page) {
         IPage<ContractsDto> contractsDtoIPage=new Page<>();
         List<ContractsDto> contractsDtos=new ArrayList<>();
         LocalDateTime start=startTime==null?null:LocalDateTime.parse(startTime+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
         LocalDateTime end=startTime==null?null:LocalDateTime.parse(endTime+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
         QueryWrapper<Contract> queryWrapper=new QueryWrapper<>();
         queryWrapper.like(null!=name,Contract.CLIENT_NAME,name)
                     .eq(null!=type,Contract.CLIENT_TYPE,type)
                     .between((null!=start)&&(end!=null),Contract.CREATE_TIME,start,end)
                     .eq(null!=status,Contract.PROGRESS,status)
                     .orderByDesc(Contract.CREATE_TIME);
        IPage<Contract> contractPage=contractMapper.selectPage(page,queryWrapper);
        List<Contract> contracts=contractPage.getRecords();
        for(Contract item:contracts){
            ContractsDto contractsDto=new ContractsDto();
            BeanUtils.copyProperties(item,contractsDto);
            String creater=userService.getUserById(item.getCreaterId()).getName();
            String progress= Progress.getStatus(item.getProgress());
            contractsDto.setCreater(creater);
            contractsDto.setProgress(progress);
            contractsDtos.add(contractsDto);
        }

        BeanUtils.copyProperties(contractPage,contractsDtoIPage);
        contractsDtoIPage.setRecords(contractsDtos);
        return contractsDtoIPage;
    }

    @Override
    public ContractDto getContract(Long id) {
        Contract contract= getContractById(id);
        Assert.notNull(contract,"合同不存在！");
        ContractDto contractDto=new ContractDto();
        BeanUtils.copyProperties(contract,contractDto);
        String creater=userService.getUserById(contract.getCreaterId()).getName();
        String progress= Progress.getStatus(contract.getProgress());
        contractDto.setCreater(creater);
        contractDto.setProgress(progress);
        return contractDto;
    }

    @Override
    public Contract getContractById(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    public List<SimpleContractDto> getAllContractName() {
        QueryWrapper<Contract> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc(Contract.CLIENT_NAME);
        List<Contract> contracts=contractMapper.selectList(queryWrapper);
        List<SimpleContractDto> simpleContractDtos=new ArrayList<>();
       for(Contract item:contracts){
           SimpleContractDto simpleContractDto=new SimpleContractDto();
           BeanUtils.copyProperties(item,simpleContractDto);
           simpleContractDtos.add(simpleContractDto);
       }
       return simpleContractDtos;
    }
}
