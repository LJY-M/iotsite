package com.lot.iotsite.controller;

import com.lot.iotsite.domain.ChartElements;
import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.Picture;
import com.lot.iotsite.domain.ProjectCheckResult;
import com.lot.iotsite.dto.CheckItemDto;
import com.lot.iotsite.dto.ProjectGradeDto;
import com.lot.iotsite.dto.UserGroupCheckDto;
import com.lot.iotsite.queryParam.CheckParam;
import com.lot.iotsite.service.CheckService;
import com.lot.iotsite.service.PictureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private PictureService pictureService;

    @GetMapping("/get_project_all_check")
    public ProjectCheckResult getProjectAllCheck(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 0);
        return projectCheckResult;
    }

    @GetMapping("/get_project_not_review_check")
    public ProjectCheckResult getProjectNotReviewCheck(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 1);
        return projectCheckResult;
    }

    @GetMapping("/get_project_not_pass_check")
    public ProjectCheckResult getProjectNotPassCheck(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 2);
        return projectCheckResult;
    }

    @GetMapping("/get_project_pass_check")
    public ProjectCheckResult getProjectPassCheck(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 3);
        return projectCheckResult;
    }

    @GetMapping("/get_check_list")
    public List<Check> getCheckItemByGroupIdAndIdentity(
            @RequestParam(value = "groupId", required = true) Long groupId,
            @RequestParam(value = "identity", required = true) Integer identity){
        List<Check> checkList = checkService.getCheckItemByGroupId(groupId, identity);
        return checkList;
    }

    @GetMapping("/get_check_list_plus")
    public List<UserGroupCheckDto> getCheckItemByUserId(
            @RequestParam(value = "userId", required = true) Long userId){
        Assert.notNull(userId, "userId不能为空！");
        List<UserGroupCheckDto> userGroupCheckDtoList = checkService.getCheckListByUserId(userId);
        return userGroupCheckDtoList;
    }

    @GetMapping("/analysis")
    public ProjectCheckResult ProjectCheckResultAnalysis(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 3);
        ProjectCheckResult projectCheckResultAnalysis = checkService.resultsAnalysis(projectCheckResult);
        return projectCheckResult;
    }

    @GetMapping("/get_all_project_grade")
    public List<ProjectGradeDto> getAllProjectGrade(){
        List<ProjectGradeDto> projectGradeDtoList = checkService.getAllProjectGrade();
        return projectGradeDtoList;
    }

    @PutMapping("/upload_result")
    public Boolean updateCheckResult(@SpringQueryMap CheckParam checkParam){
        Assert.notNull(checkParam.getId(),"Id不能为空！");
        Assert.notNull(checkParam.getProjectId(),"projectId不能为空！");
        Assert.notNull(checkParam.getGroupId(),"groupId不能为空！");
        Assert.notNull(checkParam.getUserId(),"userId不能为空！");
        Assert.notNull(checkParam.getCheckSystemId(),"checkSystemId不能为空！");
        Assert.notNull(checkParam.getGrade(),"grade不能为空！");

        Check check = new Check();

        BeanUtils.copyProperties(checkParam, check);
        check.setFinishDateTime(LocalDateTime.now());

        check.setExamState(1);

        System.out.println(check.toString());

        Boolean flag = checkService.uploadCheckResult(check);

        return flag;
    }

    @PutMapping("/review_result")
    public Integer reviewCheckResult(
            @RequestParam(value = "checkId", required = true) Long checkId,
            @RequestParam(value = "flag", required = true) Integer flag
    ){
        Assert.notNull(checkId,"checkId不能为空！");
        Assert.notNull(flag,"flag不能为空！");

        Integer reviewReturn = checkService.reviewCheckResult(checkId, flag);

        return reviewReturn;
    }

    @PostMapping("/upload_picture")
    public Integer uploadCheckPicture(
            @RequestParam(value = "checkId", required = true) Long checkId,
            @RequestParam(value = "file", required = true) MultipartFile file
            ){
        if (file.isEmpty()){
            System.out.println("图片为空！");
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/image/";
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String urlPath = "http://localhost:8080/iotsite/image/";

        Picture picture = new Picture();
        picture.setCheckId(checkId);
        picture.setUrl(fileName);

        int flag =  pictureService.insertPicture(picture);

        return flag;
    }

    @GetMapping("/get_check_item")
    public CheckItemDto getCheckItemByCheckId(
            @RequestParam(value = "checkId", required = true) Long checkId){
        CheckItemDto checkItemDto = new CheckItemDto();
        checkItemDto = checkService.getCheckItemByCheckId(checkId);
        return checkItemDto;
    }

    @GetMapping("/get_score_composition_table")
    public ChartElements getScoreCompositionTable(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ChartElements chartElements = new ChartElements();
        chartElements = checkService.getScoreCompositionTable(projectId);
        return chartElements;
    }

    @GetMapping("/get_weight_composition_table")
    public ChartElements getWeightCompositionTable(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ChartElements chartElements = new ChartElements();
        chartElements = checkService.getWeightCompositionTable(projectId);
        return chartElements;
    }

    @PutMapping("/check")
    public Boolean addCheck(@RequestParam("projectId") Long projectId,
                            @RequestParam("userId") Long userId,
                            @RequestParam("checkSystemId") Long checkSystemId,
                            @RequestParam("grade") Integer grade, @RequestParam("description") String description){
        return checkService.insertChecks(projectId,userId,checkSystemId,grade,description);
    }
}
