package com.xcuni.guizhouyl.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExecutorTest {
    ExecutorService execService = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    private String TESTKEY = "TEST-KEY";

    class TestRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                List<String> popRes = RedisClient.brpop(0, TESTKEY);
                if (popRes.size() != 0) {
                    String keyName = popRes.get(0);
                    if (keyName.equals(TESTKEY)) {
                        TestRunner2 runner2 = new TestRunner2(popRes.get(1));
                        execService.execute(runner2);
                    }
                }
            }

        }
    }

    class TestRunner2 implements Runnable {
        final private Logger LOGGER = LoggerFactory.getLogger(TestRunner2.class);
        String val;

        public TestRunner2(String v) {
            this.val = v;
        }

        @Override
        public void run() {
            try {
                sleep(1000);
                LOGGER.info("Value is " + val);
                //System.out.println("Value is " + val);
            } catch (Exception e) {

            }
        }
    }

    @Test
    public void testExecutor() {

        TestRunner runner = new TestRunner();
        //for(int i=0;i<3;i++) {
        execService.execute(runner);
        //    System.out.println("Start thread "+String.valueOf(i)+"...");
        //}
        for (int i = 1; i <= 10; i++) {
            RedisClient.lpush(TESTKEY, String.valueOf(i));
        }

        try {
            sleep(10000);
        } catch (Exception e) {

        }
    }
}
