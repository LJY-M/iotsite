package com.lot.iotsite.dto;
import lombok.Data;

import java.util.List;
@Data
public class ContractAllDto {

    private Long clientId;
    private String clientName;
    private List<ProjectAllDto> projects;
}
