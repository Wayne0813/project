package com.wayne.erp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author LV
 * @date 2018/8/13
 */
public class App {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();
        System.out.println("服务已启动...");
        System.in.read();

    }

}
