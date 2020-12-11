package com.lot.iotsite.service;

import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.dto.CheckSystemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckSystemService {
    List<CheckSystemDto> getAllCheckSystem();
    List<CheckSystem> getSubCheckSystemById(Long fatherId);
    Boolean insertCheckSystem(CheckSystem checkSystem);
    Boolean deleteCheckSystem(Long id);
    CheckSystem getCheckSystemById(Long id);
    Boolean updateCheckSystem(CheckSystem checkSystem);
    List<CheckSystemDto> getChechSystemByName(String name);
}
