package com.lot.iotsite.controller;

import com.lot.iotsite.domain.ProjectCheckResult;
import com.lot.iotsite.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @PostMapping("/check")
    public ProjectCheckResult getProjectResult(
            @RequestParam(value = "projectId", required = true) Long projectId){
        ProjectCheckResult projectCheckResult = checkService.getProjectCheckResultByProjectId(projectId, 0);
        return projectCheckResult;
    }
}
