package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.domain.Group;
import com.lot.iotsite.domain.Project;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.ProjectDto;
import com.lot.iotsite.dto.ProjectsDto;
import com.lot.iotsite.mapper.ProjectMapper;
import com.lot.iotsite.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectToCheckSystemService projectToCheckSystemService;

    @Autowired
    private CheckService checkService;

    @Override
    public IPage<ProjectsDto> getProjects(String name, Integer status, String startTime, String endTime, IPage page) {
        IPage<ProjectsDto> projectsDtoIPage=new Page<>();
        List<ProjectsDto> projectsDtos=new ArrayList<>();
        LocalDateTime start=startTime==null?null:LocalDateTime.parse(startTime+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end=startTime==null?null:LocalDateTime.parse(endTime+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(null!=name,Project.NAME,name)
                    .eq(null!=status,Project.PROGRESS,status)
                    .between((null!=start)&&(end!=null),Project.CREATE_TIME,start,end)
                    .orderByDesc(Project.CREATE_TIME);
        IPage<Project> projectIPage=projectMapper.selectPage(page,queryWrapper);
        List<Project> projects=projectIPage.getRecords();

        for(Project item:projects){
            ProjectsDto projectsDto=new ProjectsDto();
            BeanUtils.copyProperties(item,projectsDto);
            String progress= Progress.getStatus(item.getProgress());
            projectsDto.setStatus(progress);
            projectsDtos.add(projectsDto);
        }
        BeanUtils.copyProperties(projectIPage,projectsDtoIPage);
        projectsDtoIPage.setRecords(projectsDtos);
        return projectsDtoIPage;
    }

    @Override
    public ProjectDto getProjectById(Long id) {
       Project project=getProject(id);
       Assert.notNull(project,"项目不存在！");
       ProjectDto projectDto=new ProjectDto();
       BeanUtils.copyProperties(project,projectDto);
        String progress= Progress.getStatus(project.getProgress());
        projectDto.setStatus(progress);
        // 将pm_id转换给pm_name;
        User user=userService.getUserById(project.getPmId());
        projectDto.setPmName(user.getName());
        //将groupID转换为groupName
        Group group=groupService.getGroupById(project.getGroupId());
        projectDto.setGroupName(group.getName());
        //将clientIc转换为委托方名称
        Contract contract=contractService.getContractById(project.getClientId());
        projectDto.setClientName(contract.getClientName());
        // 获取添加的检查体系
        projectDto.setCheckSystems(projectToCheckSystemService.getCheckSystemNameByProject(project.getId()));
       return projectDto;
    }

    @Override
    public Boolean addProject(Project project,List<Long> fatherIds) {
      Assert.isTrue(1==projectMapper.insert(project),"添加项目失败！");
      projectToCheckSystemService.addProjectToCheckSystems(fatherIds,project.getId());
      return true;
    }

    @Override
    public Boolean updateProject(Project project,List<Long> fatherIds) {
       Assert.isTrue(1==projectMapper.updateById(project),"更新项目信息失败！");
       projectToCheckSystemService.deleteProjectToCheckSystems(project.getId());
       projectToCheckSystemService.addProjectToCheckSystems(fatherIds,project.getId());
       return true;
    }

    @Override
    public Boolean deleteProject(Long id) {
        Assert.isTrue(1==projectMapper.deleteById(id),"删除项目失败！");
        // 删除检查记录
        checkService.deleteChecksByProjectId(id);
        //删除检查体系
        projectToCheckSystemService.deleteProjectToCheckSystems(id);
        return true;
    }

    @Override
    public Project getProject(Long id) {
       return projectMapper.selectById(id);
    }

    @Override
    public Boolean deleteProjectByClienId(Long clientId) {
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Project.CLIENT_ID,clientId);
        Assert.isTrue(1<=projectMapper.delete(queryWrapper),"删除合同关联项目失败！");
        return true;
    }

    @Override
    public Project getUserProject(Long groupId) {
       QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq(Project.GROUP_ID,groupId);
       return projectMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Project> getAllProject() {

        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.orderByAsc(Project.ID);
        List<Project> projectList = projectMapper.selectList(projectQueryWrapper);
        return projectList;
    }
}
