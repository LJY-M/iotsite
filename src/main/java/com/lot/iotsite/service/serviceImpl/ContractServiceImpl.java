package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.*;
import com.lot.iotsite.dto.*;
import com.lot.iotsite.mapper.ContractMapper;
import com.lot.iotsite.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ProjectToCheckSystemService projectToCheckSystemService;

    @Autowired
    private CheckService checkService;

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
       contractMapper.deleteById(id);
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
    public List<SimpleContractDto> getAllContractName(String clientName) {
        QueryWrapper<Contract> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(null!=clientName,Contract.CLIENT_NAME,clientName)
                    .orderByAsc(Contract.CLIENT_NAME);
        List<Contract> contracts=contractMapper.selectList(queryWrapper);
        List<SimpleContractDto> simpleContractDtos=new ArrayList<>();
       for(Contract item:contracts){
           SimpleContractDto simpleContractDto=new SimpleContractDto();
           BeanUtils.copyProperties(item,simpleContractDto);
           simpleContractDtos.add(simpleContractDto);
       }
       return simpleContractDtos;
    }

    @Override
    public List<ContractAllDto> getUserContracts(Long userId) {
        List<ContractAllDto> contractAllDtos=new ArrayList<>();
        List<UserGroup> userGroups=groupService.getGroupByUser(userId);
        List<Project> projects=new ArrayList<>();
        for(UserGroup group:userGroups){
            List<Project> projects1=projectService.getUserProject(group.getGroupId());
            for(Project project:projects1) projects.add(project);
        }

        List<ProjectAllDto> projectAllDtos=new ArrayList<>();
        Set<Contract> contracts=new HashSet<>();
        for(Project project:projects){
            contracts.add(getContractByProject(project));
            ProjectAllDto projectAllDto=new ProjectAllDto();
            projectAllDto.setProjectId(project.getId());
            projectAllDto.setProjectName(project.getName());
            projectAllDto.setClientId(project.getClientId());
            List<CheckSystemDto> checkSystemdtos=projectToCheckSystemService.getCheckSystemNameByProject(project.getId());
            List<CheckSystemStatusDto> checkSystemStatusDtos=new ArrayList<>();
            for(CheckSystemDto checkSystemDto:checkSystemdtos) {
                for (CheckSystemDto checkSystemDto1 : checkSystemDto.getSubCheckSystems()) {
                    CheckSystemStatusDto checkSystemStatusDto = new CheckSystemStatusDto();
                    Check check = checkService.getCheckByProjectIdAndCheckSystemId(project.getId(), checkSystemDto1.getId());
                    BeanUtils.copyProperties(checkSystemDto1, checkSystemStatusDto);
                    checkSystemStatusDto.setExamState(check.getExamState());
                    checkSystemStatusDto.setPassState(check.getPassState());
                    checkSystemStatusDtos.add(checkSystemStatusDto);
                }
            }
            projectAllDto.setCheckSystems(checkSystemStatusDtos);
            projectAllDtos.add(projectAllDto);
        }
        for(Contract contract:contracts){
            ContractAllDto contractAllDto=new ContractAllDto();
            contractAllDto.setClientId(contract.getId());
            contractAllDto.setClientName(contract.getClientName());
            List<ProjectAllDto> projectAllDtoList=new ArrayList<>();
            for(ProjectAllDto projectAllDto:projectAllDtos){
                if(projectAllDto.getClientId()==contract.getId()){
                    projectAllDtoList.add(projectAllDto);
                }
            }
            contractAllDto.setProjects(projectAllDtoList);
            contractAllDtos.add(contractAllDto);
        }
        return contractAllDtos;
    }

    private Contract getContractByProject(Project project){
        return contractMapper.selectById(project.getClientId());
    }
}
