package com.lot.iotsite.domain;

import com.lot.iotsite.baseClass.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("项目实体")
public class Project extends BaseEntity {
    private String name;
    private Long groupId;
    private Integer risk;
    private Integer progress;
    private Long pmId;
    private String description;
    private String constructionUnit;
    private String supervisorUnit;
    private String buildUnit;
    private String clientId;
}
