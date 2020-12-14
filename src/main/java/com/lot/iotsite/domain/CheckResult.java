package com.lot.iotsite.domain;

import lombok.Data;

import java.util.List;

@Data
public class CheckResult {

//    Check item attributes
    private Check check;

//    Image item properties
    private List<Picture> pictureList;
}
