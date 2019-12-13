package com.xcuni.guizhouyl.data;

import com.xcuni.guizhouyl.data.database.entity.TblDeathDataSrcEntity;
import com.xcuni.guizhouyl.data.database.entity.TblUserStatusEntity;
import com.xcuni.guizhouyl.data.database.service.TblDeathDataSrcService;
import com.xcuni.guizhouyl.data.database.service.TblUserStatusService;
import com.xcuni.guizhouyl.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DatabaseImplTest {
    @Autowired
    TblUserStatusService userStatusService;
    @Autowired
    TblDeathDataSrcService deathDataSrcService;

    @Test
    public void testInsertUserStatusData() {
        TblUserStatusEntity entity = new TblUserStatusEntity();
        entity.setUserId("522526195502051010");
        entity.setPointS(9);
        entity.setPointT(12);
        entity.setUserStatus(1);
        entity.setStatusDesc(1);
        entity.setAliveDataSrc("04");
        entity.setNote("意外");
//        Date date = new Date();
//        Timestamp timeStamp = new Timestamp(date.getTime());
        String updTime = "2018-11-22";
        try {
            //Timestamp stamp = DatelUtils.dateToTimeStamp(updTime);
            String date = DateUtils.dateStringToDate(updTime);
            entity.setUpdateTime(date);
        } catch (Exception e) {
            System.out.println("timestamp error");
        }
        //entity.setUpdateTime(timeStamp);

        userStatusService.insertUserStatus(entity);
        System.out.println("123");
    }

    @Test
    public void testUpdateUserStatusData() {
        TblUserStatusEntity entity = new TblUserStatusEntity();
        entity.setUserId("522526195502051010");
        entity.setPointS(8);
        entity.setPointT(11);
//        entity.setUserStatus(2);
//        entity.setStatusDesc(0);
//        entity.setAliveDataSrc("");
//        entity.setNote("");
//        Date date = new Date();
//        Timestamp timeStamp = new Timestamp(date.getTime());
//        entity.setUpdateTime(timeStamp);

        userStatusService.updateUserStatus(entity);
        System.out.println("123");
    }

    @Test
    public void testQueryUserStatusData() {
        TblUserStatusEntity entity = userStatusService.getUserStatusEntityById("522526195502051010");

        System.out.println("123");
    }

    @Test
    public void testDeleteUserStatusData() {
        String userId = "522526195502051010";
        userStatusService.deleteUserStatusData(userId);
        System.out.println("123");
    }


    @Test
    public void testDeathDataSrcData() {
        TblDeathDataSrcEntity entity = new TblDeathDataSrcEntity();
        //entity.setId();
        entity.setUserId("522526195502051012");

        try {
            String date = "2018/9/01";
            String dt = DateUtils.dateStringToDate(date);
            entity.setDeathTime(dt);
            entity.setUpdateTime(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        entity.setDataSrc("03");
        entity.setDataId("02");
        entity.setNote("意外");

        deathDataSrcService.insertDeathData(entity);
        System.out.println("456");
    }

    @Test
    public void testUpdateDeathDataSrcData() {
        TblDeathDataSrcEntity entity = new TblDeathDataSrcEntity();
        //entity.setId();
        entity.setUserId("522526195502051012");

        try {
            String date = "2018/9/11";
            String dt = DateUtils.dateStringToDate(date);
            entity.setDeathTime(dt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        entity.setDataSrc("01");
        entity.setDataId("02");
        entity.setNote("生病");
//
//        try {
//            entity.setUpdateTime(timeStamp);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        deathDataSrcService.updateDeathData(entity);
        System.out.println("456");
    }

    @Test
    public void testQueryDeathData() {
        TblDeathDataSrcEntity entity = deathDataSrcService.getDeathDataEntityById("522526195502051012");

        System.out.println("123");
    }

    @Test
    public void testDeleteDeathData() {
        deathDataSrcService.deleteDeathData("522526195502051012");
        System.out.println("123");
    }
}
