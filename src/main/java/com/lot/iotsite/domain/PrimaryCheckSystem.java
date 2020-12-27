package com.lot.iotsite.domain;

import lombok.Data;

import java.util.List;

@Data
public class PrimaryCheckSystem {

//    The first level checks the system item attributes
    private CheckSystem checkSystem;

    private Double realWeight;

    private Double grade;

//    The second level checks the system item attributes
    private List<SecondaryCheckSystem> secondaryCheckSystemList;
}
