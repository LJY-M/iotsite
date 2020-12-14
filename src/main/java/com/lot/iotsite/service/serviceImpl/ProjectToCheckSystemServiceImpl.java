package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.domain.ProjectToCheckSystem;
import com.lot.iotsite.mapper.ProjectToCheckSystemMapper;
import com.lot.iotsite.service.ProjectToCheckSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectToCheckSystemServiceImpl implements ProjectToCheckSystemService {

    @Autowired
    private ProjectToCheckSystemMapper projectToCheckSystemMapper;

    @Override
    public List<ProjectToCheckSystem> getProjectToCheckSystemByProjectId(Long projectId) {

        QueryWrapper<ProjectToCheckSystem> projectToCheckSystemQueryWrapper = new QueryWrapper<>();
        projectToCheckSystemQueryWrapper.eq(ProjectToCheckSystem.PROJECT_ID, projectId);
        List<ProjectToCheckSystem> projectToCheckSystemList =
                projectToCheckSystemMapper.selectList(projectToCheckSystemQueryWrapper);

        if (projectToCheckSystemList.isEmpty())
            System.out.println(getClass() + " : " + " projectToCheckSystemList is empty ! ");


        return projectToCheckSystemList;
    }
}
