package com.lot.iotsite.service;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.domain.ProjectCheckResult;
import com.lot.iotsite.dto.UserGroupCheckDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckService {

    Check getCheckById(Long id);

//    checkFlag = 0;    get project all check item
//    checkFlag = 1;    get project not reviewed check item
//    checkFlag = 2;    get project not passed check item
//    checkFlag = 3;    get project passed check item
    ProjectCheckResult getProjectCheckResultByProjectId(Long projectId, int checkFlag);

//    checkFlag = 0;    get project all check item
//    checkFlag = 1;    get project not passed check item (member)
//    checkFlag = 2;    get project not reviewed check item (leader)
    List<Check> getCheckItemByGroupId(Long groupId, Integer checkFlag);

    ProjectCheckResult resultsAnalysis(ProjectCheckResult projectCheckResult);

    List<UserGroupCheckDto> getCheckListByUserId(Long userID);

    public Boolean uploadCheckResult(Check check);

    public Integer reviewCheckResult(Long checkId, Integer flag);

    public Boolean deleteChecksByProjectId(Long projectId);
}
