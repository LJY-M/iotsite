package com.lot.iotsite.service;

import com.lot.iotsite.domain.ProjectToCheckSystem;
import com.lot.iotsite.dto.CheckSystemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectToCheckSystemService {

    List<ProjectToCheckSystem> getProjectToCheckSystemByProjectId(Long projectId);

    /**
     * 删除项目的检查体系
     * @param projectId
     * @return
     */
    Boolean deleteProjectToCheckSystems(Long projectId);

    /**
     * 给项目添加检查体系
     * @param fatherIds
     * @param projectId
     * @return
     */
    Boolean addProjectToCheckSystems(List<Long> fatherIds,Long projectId);

    /**
     * 获取项目的所有检查体系名称
     * @param projectId
     * @return
     */
    List<CheckSystemDto> getCheckSystemNameByProject(Long projectId);
}
