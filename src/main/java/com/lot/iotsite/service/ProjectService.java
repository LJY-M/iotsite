package com.lot.iotsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lot.iotsite.domain.Project;
import com.lot.iotsite.dto.ProjectDto;
import com.lot.iotsite.dto.ProjectsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    /**
     * 获取所有项目
     * @param name
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public IPage<ProjectsDto> getProjects(String name,Integer status,String startTime, String endTime, IPage page);

    /**
     * 获取单个项目
     * @param id
     * @return
     */
    public ProjectDto getProjectById(Long id);

    /**
     * 添加一个项目
     * @param project
     * @return
     */
    public Boolean addProject(Project project, List<Long> fatherIds);

    /**
     * 更新项目信息
     * @param project
     * @return
     */
    public Boolean updateProject(Project project, List<Long> fatherIds);

    /**
     * 删除项目
     * @param id
     * @return
     */
    public Boolean deleteProject(Long id);

    /**
     * 获取项目
     * @param id
     * @return
     */
    public Project getProject(Long id);

    public Boolean deleteProjectByClienId(Long clientId);

    public Project getUserProject(Long groupId);

    public List<Project> getAllProject();
}
