package com.yutu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages ={"com.yutu.mapper"})
public class YutuApplication {

    public static void main(String[] args) {
        SpringApplication.run(YutuApplication.class, args);
    }

}
