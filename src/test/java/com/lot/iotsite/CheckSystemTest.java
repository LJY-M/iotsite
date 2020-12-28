package com.lot.iotsite;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.mapper.CheckSystemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckSystemTest {

    @Autowired
    private CheckSystemMapper checkSystemMapper;

    @Test
    public void Insert() {
        System.out.println(("----- insert method test ------"));

        List<CheckSystem> checkSystemList = new ArrayList<>();

        checkSystemList.add(getCheckSystem1());
        checkSystemList.add(getCheckSystem2());
        checkSystemList.add(getCheckSystem3());
        checkSystemList.add(getCheckSystem4());
        checkSystemList.add(getCheckSystem5());
        checkSystemList.add(getCheckSystem6());
        checkSystemList.add(getCheckSystem7());
        checkSystemList.add(getCheckSystem8());
        checkSystemList.add(getCheckSystem9());
        checkSystemList.add(getCheckSystem10());

        checkSystemList.forEach(
                item->{
                    checkSystemMapper.insert(item);
                });
    }

    @Test
    public void testSelectAll(){
        System.out.println(("----- selectAll method test ------"));
        List<CheckSystem> checkSystemList = checkSystemMapper.selectList(null);
        checkSystemList.forEach(System.out::println);
    }

    @Test
    public void testSelectById(){
        System.out.println(("----- select by id method test ------"));
        QueryWrapper<CheckSystem> checkSystemQueryWrapper = new QueryWrapper<>();
        checkSystemQueryWrapper.eq("id", 4);
        List<CheckSystem> checkSystemList = checkSystemMapper.selectList(checkSystemQueryWrapper);
        checkSystemList.forEach(System.out::println);
    }

    @Test
    public void testSelectByFatherId(){
        System.out.println(("----- select by father_id method test ------"));
        QueryWrapper<CheckSystem> checkSystemQueryWrapper = new QueryWrapper<>();
        checkSystemQueryWrapper.eq("father_id", 11);
        List<CheckSystem> checkSystemList = checkSystemMapper.selectList(checkSystemQueryWrapper);
        checkSystemList.forEach(System.out::println);
    }

    public CheckSystem getCheckSystem1() {

        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("安全检查");
        checkSystem.setWeight(3);

        return checkSystem;
    }

    public CheckSystem getCheckSystem2() {

        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("质量检查");
        checkSystem.setWeight(2);

        return checkSystem;
    }

    public CheckSystem getCheckSystem3() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("进度检查");
        checkSystem.setWeight(1);

        return checkSystem;
    }

    public CheckSystem getCheckSystem4() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("管理安全");
        checkSystem.setWeight(3);
        checkSystem.setFatherId((long) 1);

        return checkSystem;
    }

    public CheckSystem getCheckSystem5() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("施工安全");
        checkSystem.setWeight(2);
        checkSystem.setFatherId((long) 1);

        return checkSystem;
    }

    public CheckSystem getCheckSystem6() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("场地安全");
        checkSystem.setWeight(1);
        checkSystem.setFatherId((long) 1);

        return checkSystem;
    }

    public CheckSystem getCheckSystem7() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("材料质量");
        checkSystem.setWeight(1);
        checkSystem.setFatherId((long) 2);

        return checkSystem;
    }

    public CheckSystem getCheckSystem8() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("成品质量");
        checkSystem.setWeight(2);
        checkSystem.setFatherId((long) 2);

        return checkSystem;
    }

    public CheckSystem getCheckSystem9() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("工程量进度");
        checkSystem.setWeight(2);
        checkSystem.setFatherId((long) 3);

        return checkSystem;
    }

    public CheckSystem getCheckSystem10() {
        CheckSystem checkSystem = new CheckSystem();

        checkSystem.setName("资源使用进度");
        checkSystem.setWeight(1);
        checkSystem.setFatherId((long) 3);

        return checkSystem;
    }

}
