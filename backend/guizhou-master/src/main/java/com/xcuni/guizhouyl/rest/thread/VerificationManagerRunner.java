package com.xcuni.guizhouyl.rest.thread;

import com.xcuni.guizhouyl.config.YanglaoAppData;
import com.xcuni.guizhouyl.data.service.DataDictService;
import com.xcuni.guizhouyl.data.RedisClient;
import com.xcuni.guizhouyl.data.database.service.YanglaoDbOperation;
import com.xcuni.guizhouyl.data.service.DataPlatformService;
import com.xcuni.guizhouyl.data.service.RedisKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.Thread.sleep;

public class VerificationManagerRunner implements Runnable {
    final static private Logger LOGGER = LoggerFactory.getLogger(VerificationManagerRunner.class);
    private RedisKeyService redisKeyService;
    //private FectchUserInfoRequestEntity requestEntity;
    private DataDictService dataDictService;
    private DataPlatformService dataPlatformService;
    private YanglaoAppData yanglaoAppData;
    private YanglaoDbOperation yanglaoDbOperation;

    public VerificationManagerRunner(DataDictService dataDictService,
                                     DataPlatformService dataPlatformService, RedisKeyService redisKeyService,
                                     YanglaoAppData yanglaoAppData, YanglaoDbOperation yanglaoDbOperation) {
        //this.requestEntity = req;
        this.dataDictService = dataDictService;
        this.dataPlatformService = dataPlatformService;
        this.redisKeyService = redisKeyService;
        this.yanglaoAppData = yanglaoAppData;
        this.yanglaoDbOperation = yanglaoDbOperation;

    }

    @Override
    public void run() {
        LOGGER.info("This is verificaton manager thread........");
        String userDataQkey = redisKeyService.getUserDataQueueKey();
        while (true) {
            //sleep(5);
            List<String> popRes = RedisClient.brpop(0, userDataQkey);
            if (popRes != null && popRes.size() != 0) {
                String keyName = popRes.get(0);
                LOGGER.info("POP a user data...");
                if (keyName.equals(userDataQkey)) {
                    String dataQueueStr = popRes.get(1);
                    if (dataQueueStr.equals("")) {
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                        }
                        continue;
                    }
                    SingleVerificationRunner singleVerificationRunner = new SingleVerificationRunner(dataDictService, dataPlatformService,
                            redisKeyService, yanglaoAppData, yanglaoDbOperation, dataQueueStr);
                    //singleVerificationRunner.run();
                    ConsumerExecutorManager.getInstance().doExecute(singleVerificationRunner);
                    //TODO 另一种选择，在此处将验证请求发出去，由nginx分发给各client
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                    }
                }
            }

            //TODO 查看线程池的占用状况
        }
    }
}
