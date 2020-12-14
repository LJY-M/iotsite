package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.Contract;
import com.lot.iotsite.domain.Project;
import com.lot.iotsite.dto.ContractsDto;
import com.lot.iotsite.dto.ProjectDto;
import com.lot.iotsite.dto.ProjectsDto;
import com.lot.iotsite.mapper.ProjectMapper;
import com.lot.iotsite.service.ProjectService;
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
       return projectDto;
    }

    @Override
    public Boolean addProject(Project project) {
      Assert.isTrue(1==projectMapper.insert(project),"添加项目失败！");
      return true;
    }

    @Override
    public Boolean updateProject(Project project) {
       Assert.isTrue(1==projectMapper.updateById(project),"更新项目信息失败！");
       return true;
    }

    @Override
    public Boolean deleteProject(Long id) {
        Assert.isTrue(1==projectMapper.deleteById(id),"删除项目失败！");
        return true;
    }

    @Override
    public Project getProject(Long id) {
       return projectMapper.selectById(id);
    }
}
