package com.lot.iotsite.service;

import com.lot.iotsite.domain.CheckSystem;
import org.hibernate.validator.constraints.EAN;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckSystemTest {
    @Autowired
    private CheckSystemService checkSystemService;
    @Test
    void contextLoads() {
    }

    @Test
    public void getSubCSTest(){
        List<CheckSystem> list=checkSystemService.getSubCheckSystemById(102L);
        for(CheckSystem item:list){
            System.out.print(item.toString());
        }
        CheckSystem checkSystem=checkSystemService.getCheckSystemById(1025L);
        System.out.print(checkSystem.getFatherId());
    }
}
