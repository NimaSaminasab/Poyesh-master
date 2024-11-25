package org.example;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



@SpringBootApplication
public class Main implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
