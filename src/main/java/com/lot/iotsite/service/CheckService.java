package com.lot.iotsite.service;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.ProjectCheckResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckService {

    Check getCheckById(Long id);

//    checkFlag = 0;    get project all check item
//    checkFlag = 1;    get project not exam check item
//    checkFlag = 2;    get project not passed check item
    ProjectCheckResult getProjectCheckResultByProjectId(Long projectId, int checkFlag);

    List<Long> getProjectIdListByUserId(Long userID);
}
