package com.xcuni.guizhouyl.rest.thread;

import com.xcuni.guizhouyl.data.service.DataDictService;
import com.xcuni.guizhouyl.data.RedisClient;
import com.xcuni.guizhouyl.rest.entity.FectchUserInfoRequestEntity;
import com.xcuni.guizhouyl.data.service.DataPlatformService;
import com.xcuni.guizhouyl.data.service.RedisKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.List;

import static java.lang.Thread.sleep;

public class FetchUserListRunner implements Runnable {

    final static private Logger LOGGER = LoggerFactory.getLogger(FetchUserListRunner.class);
    final static private String SUCCESS_CODE = "200";
    private DataDictService dataDictService;
    private DataPlatformService dataPlatformService;// = new DataPlatformService();
    private RedisKeyService redisKeyService;
    private FectchUserInfoRequestEntity requestEntity;
    //private String serverPort;

    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    private String execStatus = "2";

    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    public String getExecStatus() {
        return execStatus;
    }

    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public FetchUserListRunner(FectchUserInfoRequestEntity req,
                               DataDictService dataDictService,
                               DataPlatformService dataPlatformService,
                               RedisKeyService redisKeyService) {
        requestEntity = req;
        this.dataPlatformService = dataPlatformService;
        this.dataDictService = dataDictService;
        this.redisKeyService = redisKeyService;
        //this.serverPort = dataPlatformService.getServerPort();
    }

    @Override
    public void run() {
        try {
            String location = requestEntity.getLocation();
            //如果是城市，那么需要遍历下属的区县，否则只包含自己
            //TODO 数据平台可能没有到区县一级的数据，那么需要在地址字典里面为贵州省添加子location列表
            //TODO 另外，要把city下的区县id列表取消掉
            List<String> allIds = dataDictService.getLocationIdList(location);
            if (allIds == null || allIds.size() < 1) {
                LOGGER.error("输入的地址编码{}无法找到，请核对！", location);
                throw new RuntimeException("FetchUserListRunner:输入的地址编码有误!");
            }
            //开始前，清除用户信息列表
            String userQkey = redisKeyService.getUserInfoQueueKey();
            RedisClient.del(userQkey);
            String totalCountKey = redisKeyService.getTotalUserCountKey();
            RedisClient.del(totalCountKey);//总用户计数
            LOGGER.info("待获取的地址列表：{}", allIds);
            for (String id : allIds) {
                LOGGER.info("开始获取{}的待验证用户列表！", id);
                FectchUserInfoRequestEntity req = requestEntity;
                req.setLocation(id);
                //将本市的计数器归零
                String userCountKey = redisKeyService.getCityUserCountKey(requestEntity.getLocation());
                RedisClient.del(userCountKey);//各城市用户计数

                //速度比较快，暂时改为串行获取用户列表
                FetchUserByCityRunner runner = new FetchUserByCityRunner(requestEntity,
                        dataPlatformService, redisKeyService);
                runner.run();
                //ProducerExecutorManager.getInstance().doExecute(runner);
                LOGGER.info("FetchUserListRunner: city{}用户列表获取完毕.", id);
                //定期查看队列长度，如小于阈值，则继续拉取
//                Long userInQ;
//                do{
//                    sleep(2000);//等待2秒
//                    userInQ = RedisClient.llen(userQkey);
//                    LOGGER.debug("当前队列中用户剩余数量:{}",userInQ);
//                }while(userInQ>200);//如果Q中数量大于200，等待

//                LOGGER.info("队列中用户数量小于阈值，继续获取下一个区县的数据...");
            }

        } catch (Exception e) {
            LOGGER.error("fetchUserInfo接口异常:{}", e.getMessage());
        }


    }

}
