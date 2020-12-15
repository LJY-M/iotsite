package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.domain.ProjectToCheckSystem;
import com.lot.iotsite.dto.CheckSystemDto;
import com.lot.iotsite.mapper.ProjectToCheckSystemMapper;
import com.lot.iotsite.service.CheckSystemService;
import com.lot.iotsite.service.ProjectToCheckSystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectToCheckSystemServiceImpl implements ProjectToCheckSystemService {

    @Autowired
    private ProjectToCheckSystemMapper projectToCheckSystemMapper;

    @Autowired
    private CheckSystemService checkSystemService;

    @Override
    public List<ProjectToCheckSystem> getProjectToCheckSystemByProjectId(Long projectId) {

        QueryWrapper<ProjectToCheckSystem> projectToCheckSystemQueryWrapper = new QueryWrapper<>();
        projectToCheckSystemQueryWrapper.eq(ProjectToCheckSystem.PROJECT_ID, projectId);
        List<ProjectToCheckSystem> projectToCheckSystemList =
                projectToCheckSystemMapper.selectList(projectToCheckSystemQueryWrapper);

        return projectToCheckSystemList;
    }

    @Override
    public Boolean deleteProjectToCheckSystems(Long projectId) {
        QueryWrapper<ProjectToCheckSystem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(ProjectToCheckSystem.PROJECT_ID,projectId);
        Assert.isTrue(1==projectToCheckSystemMapper.delete(queryWrapper),"移除项目检查体系失败！");
        return true;
    }

    @Override
    public Boolean addProjectToCheckSystems(List<Long> fatherIds, Long projectId) {
         for(Long id:fatherIds){
            ProjectToCheckSystem projectToCheckSystem=new ProjectToCheckSystem();
            projectToCheckSystem.setFatherId(id);
            projectToCheckSystem.setProjectId(projectId);
            projectToCheckSystemMapper.insert(projectToCheckSystem);
         }
         return true;
    }

    @Override
    public List<CheckSystemDto> getCheckSystemNameByProject(Long projectId) {
        List<ProjectToCheckSystem> projectToCheckSystems=this.getProjectToCheckSystemByProjectId(projectId);
        List<CheckSystem> fatherCheckSystems=new ArrayList<>();
        for(ProjectToCheckSystem item:projectToCheckSystems){
            CheckSystem checkSystem=checkSystemService.getCheckSystemById(item.getFatherId());
            fatherCheckSystems.add(checkSystem);
        }
        List<CheckSystemDto> checkSystemDtos=new ArrayList<>();
        for(CheckSystem item:fatherCheckSystems){
            CheckSystemDto checkSystemDto=new CheckSystemDto();
            BeanUtils.copyProperties(item,checkSystemDto);
            List<CheckSystem> subCheckSystems=checkSystemService.getSubCheckSystemById(item.getId());
            List<CheckSystemDto> checkSystemDtos1=new ArrayList<>();
            for(CheckSystem item1:subCheckSystems){
                CheckSystemDto checkSystemDto1=new CheckSystemDto();
                BeanUtils.copyProperties(item1,checkSystemDto1);
                checkSystemDtos1.add(checkSystemDto1);
            }
            checkSystemDto.setSubCheckSystems(checkSystemDtos1);
            checkSystemDtos.add(checkSystemDto);
        }
        return checkSystemDtos;
    }

}
