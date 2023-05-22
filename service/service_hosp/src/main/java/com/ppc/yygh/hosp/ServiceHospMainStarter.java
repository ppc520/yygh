package com.ppc.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.ppc.yygh")
@MapperScan("com.ppc.yygh.hosp.mapper")
@EnableMongoRepositories("com.ppc.yygh.hosp.repository")
public class ServiceHospMainStarter {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospMainStarter.class,args);
    }
}
