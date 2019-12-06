package com.xcuni.guizhouyl.rest.thread;

import com.xcuni.guizhouyl.config.YanglaoAppData;
import com.xcuni.guizhouyl.data.service.DataDictService;
import com.xcuni.guizhouyl.data.RedisClient;
import com.xcuni.guizhouyl.data.service.DataPlatformService;
import com.xcuni.guizhouyl.data.service.RedisKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FetchUserDataManagerRunner implements Runnable {
    final static private Logger LOGGER = LoggerFactory.getLogger(FetchUserDataManagerRunner.class);

    private RedisKeyService redisKeyService;
    //private FectchUserInfoRequestEntity requestEntity;
    private DataDictService dataDictService;
    private DataPlatformService dataPlatformService;
    private YanglaoAppData yanglaoAppData;

    public FetchUserDataManagerRunner(DataDictService dataDictService, DataPlatformService dataPlatformService,
                                      RedisKeyService redisKeyService) {
        this.dataDictService = dataDictService;
        this.dataPlatformService = dataPlatformService;
        this.redisKeyService = redisKeyService;
    }

    @Override
    public void run() {
        LOGGER.info("This is fetch user data manager thread........");
        String userQkey = redisKeyService.getUserInfoQueueKey();
        while (true) {
            //sleep(5);
            List<String> popRes = RedisClient.brpop(0, userQkey);
            if (popRes != null && popRes.size() != 0) {
                String keyName = popRes.get(0);
                LOGGER.info("POP a user...");
                if (keyName.equals(userQkey)) {
                    String userInfoStr = popRes.get(1);

                    FetchUserDataRunner userDataRunner = new FetchUserDataRunner(userInfoStr, dataDictService,
                            dataPlatformService, redisKeyService);
                    ProducerExecutorManager.getInstance().doExecute(userDataRunner);
                    //TODO 简单的控制生产速度，根据实际情况tuning
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        LOGGER.error("Sleep出错了...");
                    }
                }
            }

            //TODO 查看线程池的占用状况
        }
    }
}
