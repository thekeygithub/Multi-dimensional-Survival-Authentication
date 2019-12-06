package com.xcuni.guizhouyl;

import com.xcuni.guizhouyl.config.ConfigurationManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.xcuni.guizhouyl.dao")
public class GuizhouylApplication {
    public static void main(String[] args) {

        SpringApplication.run(GuizhouylApplication.class, args);
    }
}
