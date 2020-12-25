package com.lot.iotsite.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.Project;
import com.lot.iotsite.dto.ProjectDto;
import com.lot.iotsite.dto.ProjectsDto;
import com.lot.iotsite.queryParam.ProjectParam;
import com.lot.iotsite.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取所有项目
     * @param name
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/projects")
    public IPage<ProjectDto> getProjects(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "status",required = false) Integer status,
                                          @RequestParam(value = "startTime",required = false) String startTime, @RequestParam(value = "endTime",required = false) String endTime,
                                          @RequestParam("page") Long current){
        //TODO 校验参数
        IPage page=new Page<Project>();
        page.setCurrent(current);
        page.setSize(10);
        return projectService.getProjects(name,status,startTime,endTime,page);

    }

    /**
     * 获取单个项目
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable("id") Long id){
        return projectService.getProjectById(id);
    }

    /**
     * 添加一个项目
     * @return
     */
    @PostMapping("/project")
    public Boolean addProject(@SpringQueryMap ProjectParam projectParam){
        Project project=new Project();
        BeanUtils.copyProperties(projectParam,project);
        project.setProgress(Progress.UNDERWAY.code());
        List<Long> fatherIds=projectParam.getCheckSystems();
       return  projectService.addProject(project,fatherIds);
    }

    /**
     * 更新项目信息
     * @return
     */
    @PutMapping("/{id}")
    public Boolean updateProject(@SpringQueryMap ProjectParam projectParam,@PathVariable("id") Long id){
        Project project=new Project();
        Project project1=projectService.getProject(id);
        Assert.isTrue(!project1.getProgress().equals(Progress.FINISH.code()),"该项目已完成，无法再修改项目信息！");
        BeanUtils.copyProperties(projectParam,project);
        List<Long> fatherIds=projectParam.getCheckSystems();
        project.setId(id);
        project.setProgress(project1.getProgress());
        return projectService.updateProject(project,fatherIds);
    }

    /**
     * 删除项目
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Boolean deleteProject(@PathVariable("id") Long id){
        return projectService.deleteProject(id);
    }


}
