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
import org.springframework.web.bind.annotation.*;

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
    public IPage<ProjectsDto> getProjects(@RequestParam("name") String name,@RequestParam("status") Integer status,
                                          @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
                                          @RequestParam("page.current") Long current){
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
       return  projectService.addProject(project);
    }

    /**
     * 更新项目信息
     * @return
     */
    @PutMapping("/project")
    public Boolean updateProject(@SpringQueryMap ProjectParam projectParam){
        Project project=new Project();
        BeanUtils.copyProperties(projectParam,project);
        return projectService.updateProject(project);
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
