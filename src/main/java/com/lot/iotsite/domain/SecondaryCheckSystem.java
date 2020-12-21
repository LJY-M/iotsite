package com.lot.iotsite.domain;

import lombok.Data;

import java.util.List;

@Data
public class SecondaryCheckSystem {

//    The second level checks the system item attributes
    private CheckSystem checkSystem;

    private Double grade;

//    The check result item properties
    private List<CheckResult> checkResultList;
}
