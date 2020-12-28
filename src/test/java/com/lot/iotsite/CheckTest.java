package com.lot.iotsite;

import com.lot.iotsite.domain.Check;
import com.lot.iotsite.mapper.CheckMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckTest {

    @Autowired
    private CheckMapper checkMapper;

    @Test
    public void insertTest(){

        System.out.println(("----- insert method test ------"));

        List<Check> checkList = new ArrayList<>();

        checkList.add(getCheck1());
        checkList.add(getCheck2());
        checkList.add(getCheck3());
        checkList.add(getCheck4());
        checkList.add(getCheck5());
        checkList.add(getCheck6());

        checkList.forEach(
                item->{
                    checkMapper.insert(item);
                }
        );
    }

    private Check getCheck1(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 4);
        check.setGrade(80);
        check.setDescription("LJY-Test");
        check.setExamState(1);
        check.setPassState(1);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }

    private Check getCheck2(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 5);
        check.setGrade(80);
        check.setDescription("LJY-Test");
        check.setExamState(1);
        check.setPassState(1);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }

    private Check getCheck3(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 6);
        check.setGrade(80);
        check.setDescription("LJY-Test");
        check.setExamState(1);
        check.setPassState(1);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }
    private Check getCheck4(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 7);
        check.setGrade(80);
        check.setDescription("LJY-Test");
        check.setExamState(1);
        check.setPassState(1);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }
    private Check getCheck5(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 8);
//        check.setGrade(80);
        check.setDescription("LJY-Test");
        check.setExamState(0);
        check.setPassState(0);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }
    private Check getCheck6(){
        Check check = new Check();
        check.setProjectId((long) 1);
        check.setGroupId((long) 111);
        check.setUserId((long) 222);
        check.setCheckSystemId((long) 12);
        check.setGrade(50);
        check.setDescription("LJY-Test");
        check.setExamState(1);
        check.setPassState(0);
        check.setFinishDateTime(LocalDateTime.now());
        return check;
    }
}
