package com.lot.iotsite.domain;

import lombok.Data;

import java.util.List;

@Data
public class ProjectCheckResult {

//    Project item attribute
    private Project project;

    private Double grade;

//    The first level checks the system item attributes
    private List<PrimaryCheckSystem> primaryCheckSystemList;
}
