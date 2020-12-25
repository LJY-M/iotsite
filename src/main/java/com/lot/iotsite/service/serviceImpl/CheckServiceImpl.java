package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.*;
import com.lot.iotsite.dto.ProjectGradeDto;
import com.lot.iotsite.dto.UserGroupCheckDto;
import com.lot.iotsite.dto.UserGroupDto;
import com.lot.iotsite.mapper.CheckMapper;
import com.lot.iotsite.mapper.PictureMapper;
import com.lot.iotsite.mapper.ProjectMapper;
import com.lot.iotsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Dictionary;
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

    @Autowired
    private PictureService pictureService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ProjectService projectService;

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
                                    checkQueryWrapper.eq(Check.PASS_STATE, 0);
                                    checkQueryWrapper.eq(Check.EXAM_STATE, 1);
                                }
                                if (checkFlag == 2){
//                                    checkQueryWrapper.and(
//                                            checkQueryWrapper1 ->
//                                                    checkQueryWrapper1.eq(Check.EXAM_STATE, 0)
//                                                            .or()
//                                                            .eq(Check.PASS_STATE, 0)
//                                    );
                                    checkQueryWrapper.eq(Check.PASS_STATE, 0);
                                }
                                if (checkFlag == 3){
                                    checkQueryWrapper.eq(Check.PASS_STATE, 1);
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
    public List<Check> getCheckItemByGroupId(Long groupId, Integer checkFlag) {

        QueryWrapper<Check> checkQueryWrapper = new QueryWrapper<>();
        checkQueryWrapper.eq(Check.GROUP_ID, groupId);


        if (checkFlag == 2){
            checkQueryWrapper.eq(Check.PASS_STATE, 0);
            checkQueryWrapper.eq(Check.EXAM_STATE, 1);
        }
        if (checkFlag == 1){

            checkQueryWrapper.eq(Check.PASS_STATE, 0);
        }

        List<Check> checkList = checkMapper.selectList(checkQueryWrapper);

        return checkList;
    }

    @Override
    public List<UserGroupCheckDto> getCheckListByUserId(Long userID) {

        //查询user所在group
        List<UserGroup> userGroupList = groupService.getGroupByUser(userID);

        List<UserGroupCheckDto> userGroupCheckDtoList = new ArrayList<>();

        for (int i = 0; i < userGroupList.size(); i++){

            UserGroupCheckDto userGroupCheckDto = new UserGroupCheckDto();

            UserGroup userGroup = userGroupList.get(i);

            List<Check> checkList = getCheckItemByGroupId(userGroup.getGroupId(),
                    userGroup.getIsLeader() + 1);

            userGroupCheckDto.setUserGroup(userGroup);
            userGroupCheckDto.setCheckList(checkList);

            userGroupCheckDtoList.add(userGroupCheckDto);
        }

        return userGroupCheckDtoList;
    }

    @Override
    public Boolean uploadCheckResult(Check check) {
        Check check1 = getCheckById(check.getId());
        Assert.notNull(check1, "该检查结果不存在！");

        Assert.isTrue(check1.getExamState() == 0, "审核前不能重复提交");

        Assert.isTrue(check1.getPassState() == 0, "审核通过后不能提交");

        Assert.isTrue(1 == checkMapper.updateById(check), "检查结果更新失败！");
        return true;
    }

    @Override
    public Integer reviewCheckResult(Long checkId, Integer flag) {
        Check check = getCheckById(checkId);
        Assert.notNull(check, "该检查结果不存在！");

        Assert.isTrue(check.getExamState() == 1, "提交前不能审核");

        Assert.isTrue(check.getPassState() == 0, "审核通过后不能提交");

        Integer reviewReturn = -1;

        if (flag == 0) {
            check.setExamState(0);
            reviewReturn = checkMapper.updateById(check);
        }
        else if(flag == 1) {
            check.setPassState(1);
            reviewReturn = checkMapper.updateById(check);
        }
        else {
            Assert.notNull(null, "该标志不存在！");
        }


        return reviewReturn;
    }

    @Override
    public ProjectCheckResult resultsAnalysis(ProjectCheckResult projectCheckResult) {

        Integer secondCheckSystemNum = 0;
        Integer checkNum = 0;

        List<PrimaryCheckSystem> primaryCheckSystemList = projectCheckResult.getPrimaryCheckSystemList();

        int k = 0;
        double primaryWeight[] = new double[10];
        double primarySumWeight = 0;
        double primarySum = 0;

        for ( k = 0; k < primaryCheckSystemList.size(); k++){
            primaryWeight[k] = primaryCheckSystemList.get(k).getCheckSystem().getWeight();
            primarySumWeight += primaryWeight[k];
        }

        for (k = 0; k < primaryCheckSystemList.size(); k++){

            //获取一级检查体系
            CheckSystem checkSystem = primaryCheckSystemList.get(k).getCheckSystem();

            double primarySumGrade = 0;
            primaryWeight[k] = primaryWeight[k] / primarySumWeight;

            List<SecondaryCheckSystem> secondaryCheckSystemList =
                    primaryCheckSystemList.get(k).getSecondaryCheckSystemList();


            int j = 0;
            double secondWeight[] = new double[10];
            double secondSumWeight = 0;
            double secondSum = 0;

            for ( j = 0; j < secondaryCheckSystemList.size(); j++){
                secondWeight[j] = secondaryCheckSystemList.get(j).getCheckSystem().getWeight();
                secondSumWeight += secondWeight[j];
                secondCheckSystemNum ++;
            }

            for ( j = 0; j < secondaryCheckSystemList.size(); j++){

                CheckSystem checkSystem1 = secondaryCheckSystemList.get(j).getCheckSystem();

                double secondSumGrade = 0;
                secondWeight[j] = secondWeight[j] / secondSumWeight;

                List<CheckResult> checkResultList = secondaryCheckSystemList.get(j).getCheckResultList();



                for (int i = 0; i < checkResultList.size(); i++){

                    Check check = checkResultList.get(i).getCheck();
                    Integer checkGrade = check.getGrade();
                    secondSumGrade = checkGrade * secondWeight[j];

                    checkNum++;

                    secondaryCheckSystemList.get(j).setGrade(secondSumGrade);
                }

                secondSum = secondSum + secondSumGrade;

            }
            primaryCheckSystemList.get(k).setGrade(secondSum);

            primarySumGrade = secondSum * primaryWeight[k];

            primarySum = primarySum + primarySumGrade;

        }
        projectCheckResult.setGrade(primarySum);

        System.out.println(" 测试项目是否完成 " + secondCheckSystemNum + " : " + checkNum);

        return projectCheckResult;

    }

    @Override
    public Boolean deleteChecksByProjectId(Long projectId) {
       QueryWrapper<Check> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq(Check.PROJECT_ID,projectId);
       checkMapper.delete(queryWrapper);
       // 删除检查图片
        List<Check> checks=getChecksByProjectId(projectId);
        for(Check item:checks){
            pictureService.deletePictureByCheckId(item.getId());
        }
       return true;
    }

    private List<Check> getChecksByProjectId(Long projectId){
        QueryWrapper<Check> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Check.PROJECT_ID,projectId);
        return checkMapper.selectList(queryWrapper);
    }

    @Override
    public List<ProjectGradeDto> getAllProjectGrade() {

        List<Project> projectList = projectService.getAllProject();

        List<ProjectGradeDto> projectGradeDtoList = new ArrayList<>();

        for (int i = 0; i < projectList.size(); i++){

            ProjectGradeDto projectGradeDto = new ProjectGradeDto();

//            ProjectCheckResult projectCheckResult = new ProjectCheckResult();
            ProjectCheckResult projectCheckResultAnalysis = new ProjectCheckResult();

            Project project = projectList.get(i);

            ProjectCheckResult projectCheckResult = getProjectCheckResultByProjectId(project.getId(), 3);
            projectCheckResultAnalysis = resultsAnalysis(projectCheckResult);


            projectGradeDto.setProject(project);
            projectGradeDto.setGrade(projectCheckResultAnalysis.getGrade());

            projectGradeDtoList.add(projectGradeDto);
        }

        return projectGradeDtoList;
    }
}
