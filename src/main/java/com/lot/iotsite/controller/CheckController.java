package com.lot.iotsite.controller;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.Picture;
import com.lot.iotsite.domain.ProjectCheckResult;
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
import java.util.UUID;

@RestController
//@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private PictureService pictureService;

    @PostMapping("/check")
    public ProjectCheckResult getProjectResult(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 0);
        return projectCheckResult;
    }

    @PostMapping("/check2")
    public ProjectCheckResult getProjectResult2(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 1);
        return projectCheckResult;
    }

    @PostMapping("/check3")
    public ProjectCheckResult getProjectResult3(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 2);
        return projectCheckResult;
    }

    @PostMapping("/check/update")
    public Boolean updateCheckResult(@SpringQueryMap CheckParam checkParam){
        Assert.notNull(checkParam.getId(),"Id不能为空！");
        Assert.notNull(checkParam.getProjectId(),"projectId不能为空！");
        Assert.notNull(checkParam.getGroupId(),"groupId不能为空！");
        Assert.notNull(checkParam.getUserId(),"userId不能为空！");
        Assert.notNull(checkParam.getCheckSystemId(),"checkSystemId不能为空！");
        Assert.notNull(checkParam.getGrade(),"grade不能为空！");
        Assert.notNull(checkParam.getExamState(),"examState不能为空！");
        Assert.notNull(checkParam.getPassState(),"passState不能为空！");

        Check check = new Check();
        BeanUtils.copyProperties(checkParam, check);
        check.setFinishDateTime(LocalDateTime.now());

        System.out.println(check.toString());

        Boolean flag = checkService.updateCheckResult(check);
//        if (flag.equals(true)){
//            return " The update is successful : \n" + check.toString();
//        }
//
//        return "Update failed";
        return flag;
    }

    @PostMapping("/check/upload_picture")
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

        Picture picture = new Picture();
        picture.setCheckId(checkId);
        picture.setUrl(fileName);

        int flag =  pictureService.insertPicture(picture);

        return flag;
    }
}
