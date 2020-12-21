package com.lot.iotsite.queryParam;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckParam {

    private Long id;

    private Long projectId;

    private Long groupId;

    private Long userId;

    private Long checkSystemId;

    private Integer grade;

    private String description;

    private Integer examState;

    private Integer passState;

    private LocalDateTime finishDateTime;
}
