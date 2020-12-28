package com.lot.iotsite;

import com.lot.iotsite.domain.Project;
import com.lot.iotsite.mapper.ProjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectSampleTest {

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void insertTest() {
        System.out.println(("----- insert method test ------"));

        Project project = new Project();
        project.setName("LJY-Test");
        project.setGroupId((long) 111);
        project.setRisk(1);
        project.setProgress(1);
        project.setPmId((long) 222);
        project.setDescription("LJY-Test");
        project.setConstructionUnit("LJY");
        project.setSupervisorUnit("LJY");
        project.setClientId((long) 333);

        projectMapper.insert(project);
    }
}
