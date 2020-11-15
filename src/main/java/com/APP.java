package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * description：
 * author：dingyawu
 * date：created in 17:10 2020/11/15
 * history:
 */
@SpringBootApplication
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
    }

}
