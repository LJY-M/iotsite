package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.*;
import com.lot.iotsite.mapper.CheckMapper;
import com.lot.iotsite.mapper.CheckSystemMapper;
import com.lot.iotsite.mapper.PictureMapper;
import com.lot.iotsite.mapper.ProjectMapper;
import com.lot.iotsite.service.CheckService;
import com.lot.iotsite.service.CheckSystemService;
import com.lot.iotsite.service.ProjectService;
import com.lot.iotsite.service.ProjectToCheckSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CheckSystemService checkSystemService;

    @Autowired
    ProjectToCheckSystemService projectToCheckSystemService;

    @Override
    public Check getCheckById(Long id) {
        return checkMapper.selectById(id);
    }

    @Override
    public ProjectCheckResult getProjectCheckResultByProjectId(Long projectId, int checkFlag) {

        ProjectCheckResult projectCheckResult = new ProjectCheckResult();

        //获取项目->一级体系表对应项
        List<ProjectToCheckSystem> projectToCheckSystemList =
                projectToCheckSystemService.getProjectToCheckSystemByProjectId(projectId);

        if (projectToCheckSystemList.isEmpty()){
            System.out.println(getClass() + " : projectToCheckSystemList is empty ! ");
            return projectCheckResult;
        }


        //获取项目对应的一级检查体系列表
        List<CheckSystem> checkSystemLevelFirstList = new ArrayList<>();

        projectToCheckSystemList.forEach(
                item->{
                    CheckSystem checkSystem = checkSystemService.getCheckSystemById(item.getFatherId());
                    if (checkSystem != null)
                        checkSystemLevelFirstList.add(checkSystem);
                }
        );

        if (checkSystemLevelFirstList.isEmpty()){
            System.out.println(getClass() + " : checkSystemLevelFirstList is empty ! ");
            return projectCheckResult;
        }


        List<PrimaryCheckSystem> primaryCheckSystemList = new ArrayList<>();

        checkSystemLevelFirstList.forEach(
                primaryCheckSystemItem->{
                    //获取一级检查体系对应的二级体系列表
                    List<CheckSystem> checkSystemList =
                            checkSystemService.getSubCheckSystemById(primaryCheckSystemItem.getId());

                    if (checkSystemList.isEmpty()){
                        System.out.println(getClass() + " : checkSystemList is empty ! ");
                        return;
                    }

                    List<SecondaryCheckSystem> secondaryCheckSystemList = new ArrayList<>();

                    checkSystemList.forEach(
                            secondaryCheckSystemItem->{

                                //封装对检查结果查询的条件
                                QueryWrapper<Check> checkQueryWrapper = new QueryWrapper<>();
                                checkQueryWrapper.eq(Check.CHECK_SYSTEM_ID, secondaryCheckSystemItem.getId());
                                checkQueryWrapper.eq(Check.PROJECT_ID, projectId);

                                if (checkFlag == 1){
                                    checkQueryWrapper.eq(Check.EXAM_STATE, 0);
                                }
                                if (checkFlag == 2){
                                    checkQueryWrapper.and(
                                            checkQueryWrapper1 ->
                                                    checkQueryWrapper1.eq(Check.EXAM_STATE, 0)
                                                            .or()
                                                            .eq(Check.PASS_STATE, 0)
                                    );
                                }

                                //获取二级体系表对应的检查记录
                                List<Check> checkList = checkMapper.selectList(checkQueryWrapper);

                                if (checkList.isEmpty()) {
                                    System.out.println(getClass() + " : checkList is empty ! ");
//                                    return;
                                }

                                List<CheckResult> checkResultList = new ArrayList<>();

                                checkList.forEach(
                                        checkResultItem->{
                                            QueryWrapper<Picture> pictureQueryWrapper = new QueryWrapper<>();
                                            pictureQueryWrapper.eq(Picture.CHECK_ID, checkResultItem.getId());
                                            List<Picture> pictureList = pictureMapper.selectList(pictureQueryWrapper);

                                            if (pictureList.isEmpty()){
                                                System.out.println(getClass() + " : pictureList is empty ! ");
//                                                return;
                                            }

                                            CheckResult checkResult = new CheckResult();
                                            checkResult.setCheck(checkResultItem);
                                            checkResult.setPictureList(pictureList);

                                            checkResultList.add(checkResult);
                                        }
                                );

                                if (checkResultList.isEmpty()){
                                    System.out.println(getClass() + " : checkResultList is empty ! ");
//                                    return;
                                }

                                SecondaryCheckSystem secondaryCheckSystem = new SecondaryCheckSystem();
                                secondaryCheckSystem.setCheckSystem(secondaryCheckSystemItem);
                                secondaryCheckSystem.setCheckResultList(checkResultList);

                                secondaryCheckSystemList.add(secondaryCheckSystem);
                            }
                    );

                    if (secondaryCheckSystemList.isEmpty()){
                        System.out.println(getClass() + " : secondaryCheckSystemList is empty ! ");
//                        return;
                    }

                    PrimaryCheckSystem primaryCheckSystem = new PrimaryCheckSystem();
                    primaryCheckSystem.setCheckSystem(primaryCheckSystemItem);
                    primaryCheckSystem.setSecondaryCheckSystemList(secondaryCheckSystemList);

                    primaryCheckSystemList.add(primaryCheckSystem);
                }
        );

        if (primaryCheckSystemList.isEmpty()){
            System.out.println(getClass() + " : primaryCheckSystemList is empty ! ");
            return projectCheckResult;
        }

        projectCheckResult.setPrimaryCheckSystemList(primaryCheckSystemList);

        Project project = projectMapper.selectById(projectId);

        if (project == null){
            System.out.println(getClass() + " : checkResultList is empty ! ");
            return projectCheckResult;
        }



//        ProjectCheckResult projectCheckResult = new ProjectCheckResult();
        projectCheckResult.setProject(project);


        return  projectCheckResult;

    }

    @Override
    public List<Long> getProjectIdListByUserId(Long userID) {

        //判断user是否为组长

        List<Long> projectId = new ArrayList<>();

        QueryWrapper<Check> checkQueryWrapper = new QueryWrapper<>();

        checkQueryWrapper.eq(Check.USER_ID, userID);

        //查询user所在group

        //是组长

        //不是组长

        return null;
    }
}
