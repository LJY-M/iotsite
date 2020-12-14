package com.lot.iotsite.service;

import com.lot.iotsite.domain.ProjectToCheckSystem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectToCheckSystemService {

    List<ProjectToCheckSystem> getProjectToCheckSystemByProjectId(Long projectId);
}
