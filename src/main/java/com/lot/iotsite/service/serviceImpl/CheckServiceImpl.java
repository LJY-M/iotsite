package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.constant.Progress;
import com.lot.iotsite.domain.*;
import com.lot.iotsite.dto.CheckItemDto;
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
    private UserService userService;

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

            Group group = groupService.getGroupById(userGroup.getGroupId());

            List<Check> checkList = getCheckItemByGroupId(userGroup.getGroupId(),
                    userGroup.getIsLeader() + 1);

            List<CheckResult> checkResultList = new ArrayList<>();

            for (int j = 0; j < checkList.size(); j++){
                CheckResult checkResult = new CheckResult();

                Check check = new Check();
                check = checkList.get(j);

                List<Picture> pictureList = pictureService.getPictureByCheckId(check.getId());

                checkResult.setCheck(check);
                checkResult.setPictureList(pictureList);

                checkResultList.add(checkResult);
            }

            userGroupCheckDto.setUserGroup(userGroup);
            userGroupCheckDto.setGroup(group);
            userGroupCheckDto.setCheckResultList(checkResultList);

            userGroupCheckDtoList.add(userGroupCheckDto);
        }

        return userGroupCheckDtoList;
    }

    public Check getCheckByProjectIDAndCheckSystemId(Check check){
        Long projectId = check.getProjectId();
        Long checkSystemId = check.getCheckSystemId();

        Check checkResult = new Check();
        QueryWrapper<Check> checkQueryWrapper = new QueryWrapper<>();
        checkQueryWrapper.eq(Check.PROJECT_ID, projectId);
        checkQueryWrapper.eq(Check.CHECK_SYSTEM_ID, checkSystemId);
        checkResult = checkMapper.selectOne(checkQueryWrapper);

        return checkResult;
    }

    @Override
    public Boolean uploadCheckResult(Check check) {
//        Check check1 = getCheckById(check.getId());
        Check check1 = getCheckByProjectIDAndCheckSystemId(check);
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

            primaryCheckSystemList.get(k).setRealWeight(primaryWeight[k]);

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

                secondaryCheckSystemList.get(j).setRealWeight(secondWeight[j]);

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
    public Check getCheckByProjectIdAndCheckSystemId(Long projectId, Long checkSystemId) {
        QueryWrapper<Check> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Check.PROJECT_ID,projectId);
        queryWrapper.eq(Check.CHECK_SYSTEM_ID,checkSystemId);
        return checkMapper.selectOne(queryWrapper);
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

            Group group = groupService.getGroupById(project.getGroupId());

            projectGradeDto.setProject(project);
            projectGradeDto.setGrade(projectCheckResultAnalysis.getGrade());

            projectGradeDto.setProjectId(project.getId());
            projectGradeDto.setProjectName(project.getName());
            projectGradeDto.setBuildUnit(project.getBuildUnit());
            projectGradeDto.setConstructionUnit(project.getConstructionUnit());
            projectGradeDto.setSupervisorUnit(project.getSupervisorUnit());
            projectGradeDto.setGroupName(group.getName());
            projectGradeDto.setStatus(Progress.getStatus(project.getProgress()));

            projectGradeDtoList.add(projectGradeDto);
        }

        return projectGradeDtoList;
    }

    @Override
    public List<CheckItemDto> getCheckItemByProjectId(Long projectId) {

        List<CheckItemDto> checkItemDtoList = new ArrayList<>();

        ProjectCheckResult projectCheckResult = new ProjectCheckResult();
        projectCheckResult = getProjectCheckResultByProjectId(projectId, 0);

        List<PrimaryCheckSystem> primaryCheckSystemList = projectCheckResult.getPrimaryCheckSystemList();

        for (int i = 0; i < primaryCheckSystemList.size(); i++){
            PrimaryCheckSystem primaryCheckSystem = primaryCheckSystemList.get(i);
            List<SecondaryCheckSystem> secondaryCheckSystemList = primaryCheckSystem.getSecondaryCheckSystemList();

            for (int j = 0; j < secondaryCheckSystemList.size(); j++){
                SecondaryCheckSystem secondaryCheckSystem = secondaryCheckSystemList.get(j);
                List<CheckResult> checkResultList = secondaryCheckSystem.getCheckResultList();

                for (int k = 0; k < checkResultList.size(); k++){
                    CheckResult checkResult = checkResultList.get(k);
                    Long checkId = checkResult.getCheck().getId();

                    CheckItemDto checkItemDto = getCheckItemByCheckId(checkId);

                    checkItemDtoList.add(checkItemDto);
                }
            }
        }

        return checkItemDtoList;
    }

    @Override
    public CheckItemDto getCheckItemByCheckId(Long checkId) {

        CheckItemDto checkItemDto = new CheckItemDto();

        Check check = new Check();
        check = getCheckById(checkId);

        CheckSystem firstCheckSystem = new CheckSystem();
        CheckSystem secondCheckSystem = new CheckSystem();

        secondCheckSystem = checkSystemService.getCheckSystemById(check.getCheckSystemId());
        firstCheckSystem = checkSystemService.getCheckSystemById(secondCheckSystem.getFatherId());

        List<Picture> pictureList = new ArrayList<>();
        List<String> pictureUrlList = new ArrayList<>();

        pictureList = pictureService.getPictureByCheckId(checkId);
        for (int i = 0; i < pictureList.size(); i++){
            String pictureUrl = "";
            pictureUrl = pictureList.get(i).getUrl();
            pictureUrlList.add(pictureUrl);
        }

        String checkRisk = "";

        if (check.getGrade() == 0)
            checkRisk = "无风险";
        else if (check.getGrade() == 1)
            checkRisk = "轻度风险";
        else if (check.getGrade() == 2)
            checkRisk = "一般风险";
        else if (check.getGrade() == 3)
            checkRisk = "高危风险";

        checkItemDto.setCheck(check);
        checkItemDto.setCheckRisk(checkRisk);
        checkItemDto.setCheckSystemName( firstCheckSystem.getName() + "-" + secondCheckSystem.getName());
        checkItemDto.setPictureUrlList(pictureUrlList);
        checkItemDto.setFirstCheckSystem(firstCheckSystem);
        checkItemDto.setSecondCheckSystem(secondCheckSystem);

        Long userId = new Long(0);
        userId = check.getUserId();
        User user = new User();
        user = userService.getUserById(userId);

        checkItemDto.setCheckId(check.getId());
        checkItemDto.setFinishDataTime(check.getFinishDateTime());

        if (user == null)
            checkItemDto.setUserName("");
        else
            checkItemDto.setUserName(user.getName());

        return checkItemDto;
    }

    @Override
    public ChartElements getScoreCompositionTable(Long projectId) {

        ChartElements chartElements = new ChartElements();

        ProjectCheckResult projectCheckResult = new ProjectCheckResult();
        ProjectCheckResult projectCheckResultAnalysis = new ProjectCheckResult();

        projectCheckResult = getProjectCheckResultByProjectId(projectId, 0);
        projectCheckResultAnalysis = resultsAnalysis(projectCheckResult);

        List<ChartElement> firstChartElements = new ArrayList<>();
        List<ChartElement> secondChartElements = new ArrayList<>();

        List<PrimaryCheckSystem> primaryCheckSystemList = projectCheckResultAnalysis.getPrimaryCheckSystemList();

        for (int i = 0; i < primaryCheckSystemList.size(); i++){

            PrimaryCheckSystem primaryCheckSystem = primaryCheckSystemList.get(i);

            Double grade =  primaryCheckSystem.getGrade();
            double weight = primaryCheckSystem.getRealWeight();

            Double weightedScore = grade * weight;

            ChartElement chartElement = new ChartElement();

            chartElement.setElementName(primaryCheckSystem.getCheckSystem().getName());
            chartElement.setElementValue(weightedScore);

            firstChartElements.add(chartElement);

            List<SecondaryCheckSystem> secondaryCheckSystemList = primaryCheckSystem.getSecondaryCheckSystemList();

            for (int j = 0; j < secondaryCheckSystemList.size(); j++){

                SecondaryCheckSystem secondaryCheckSystem = secondaryCheckSystemList.get(j);

                Double gradeSecond = secondaryCheckSystem.getGrade();
                double weightSecond = secondaryCheckSystem.getRealWeight();

//                Double weightedScoreSecond = gradeSecond * weightSecond * weight;
                Double weightedScoreSecond = gradeSecond * weight;

                ChartElement chartElementSecond = new ChartElement();

                chartElementSecond.setElementName(secondaryCheckSystem.getCheckSystem().getName());
                chartElementSecond.setElementValue(weightedScoreSecond);

                secondChartElements.add(chartElementSecond);
            }
        }

        chartElements.setFirstChartElements(firstChartElements);
        chartElements.setSecondChartElements(secondChartElements);

        return chartElements;
    }

    @Override
    public ChartElements getWeightCompositionTable(Long projectId) {

        ChartElements chartElements = new ChartElements();

        ProjectCheckResult projectCheckResult = new ProjectCheckResult();
        ProjectCheckResult projectCheckResultAnalysis = new ProjectCheckResult();

        projectCheckResult = getProjectCheckResultByProjectId(projectId, 0);
        projectCheckResultAnalysis = resultsAnalysis(projectCheckResult);

        List<ChartElement> firstChartElements = new ArrayList<>();
        List<ChartElement> secondChartElements = new ArrayList<>();

        List<PrimaryCheckSystem> primaryCheckSystemList = projectCheckResultAnalysis.getPrimaryCheckSystemList();

        for (int i = 0; i < primaryCheckSystemList.size(); i++){

            PrimaryCheckSystem primaryCheckSystem = primaryCheckSystemList.get(i);

            double weight = primaryCheckSystem.getRealWeight();

            Double weightedScore = weight;

            ChartElement chartElement = new ChartElement();

            chartElement.setElementName(primaryCheckSystem.getCheckSystem().getName());
            chartElement.setElementValue(weightedScore);

            firstChartElements.add(chartElement);

            List<SecondaryCheckSystem> secondaryCheckSystemList = primaryCheckSystem.getSecondaryCheckSystemList();

            for (int j = 0; j < secondaryCheckSystemList.size(); j++){

                SecondaryCheckSystem secondaryCheckSystem = secondaryCheckSystemList.get(j);

                double weightSecond = secondaryCheckSystem.getRealWeight();

                Double weightedScoreSecond = weightSecond * weight;

                ChartElement chartElementSecond = new ChartElement();

                chartElementSecond.setElementName(secondaryCheckSystem.getCheckSystem().getName());
                chartElementSecond.setElementValue(weightedScoreSecond);

                secondChartElements.add(chartElementSecond);
            }
        }

        chartElements.setFirstChartElements(firstChartElements);
        chartElements.setSecondChartElements(secondChartElements);

        return chartElements;
    }

    @Override
    public Boolean insertChecks(Long projectId, Long userId, Long checkSystemId, Integer grade, String description) {
        QueryWrapper<Check> wrapper=new QueryWrapper<>();
        wrapper.eq(Check.PROJECT_ID,projectId)
                .eq(Check.CHECK_SYSTEM_ID,checkSystemId);
        Check check=new Check();
        check.setCheckSystemId(checkSystemId);
        check.setDescription(description);
        check.setGrade(grade);
        checkMapper.update(check,wrapper);
        return true;
    }

    @Override
    public Boolean insertCheck(Check check) {
        check.setExamState(1);
        Assert.isTrue(1==checkMapper.insert(check),"创建项目失败!");
        return true;
    }
}
