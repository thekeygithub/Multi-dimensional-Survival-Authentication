package com.xcuni.guizhouyl;


import com.xcuni.guizhouyl.fabric.FabricManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
@Order(value = 1)
public class ApplicationSetup implements ApplicationRunner {

    final static Logger LOGGER = LoggerFactory.getLogger(ApplicationSetup.class);
    @Autowired
    FabricManager fabricManager;

    final public static int YANGLAO_APP_INTEGRATED = 0;
    final public static int YANGLAO_APP_PRODUCER = 1;
    final public static int YANGLAO_APP_CONSUMER = 2;

    @Value("${spring.datasource.name}")
    private String dbName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPwd;

    public static int ProducerThreadPoolSize = 10;
    public static int ConsumerThreadPoolSize = 10;

    //定义各委办局ID
    final public static String YBJ_ID = "02";
    final public static String SBJ_ID = "01";
    final public static String MZJ_ID = "04";
    final public static String GAJ_ID = "03";
    final public static String JTJ_ID = "05";
    final public static String YYS_ID = "06";

    //定义数据类型
    final public static String verification_data = "0"; //验证数据
    final public static String verification_result = "1";//验证结果
    //定义操作数据行为
    final public static String upload_flag = "0"; //上传
    final public static String query_flag = "1";//查询


    @Override
    public void run(ApplicationArguments var1) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPwd);
            System.out.println("数据库连接成功!");
        } catch (Exception e) {
            LOGGER.error("连接数据库失败，请检查配置！");
            //throw new RuntimeException(e.getMessage());
        }
        //初始化fabric连接
        fabricManager.setup();
        System.out.println("应用初始化结束!");
    }
    /*
        dev-peer0.org1.guizhou.com-dataCC-1.0   dev-peer0.org1.guizhou.com-traceCc-1.0   dev-peer0.org1.guizhou.com-dataCc-1.0
     */
}


