package com.varb.schedule;

import com.varb.schedule.config.MyApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ScheduleApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ScheduleApplication.class, args);

        new SpringApplicationBuilder(ScheduleApplication.class)
                .initializers(new MyApplicationContextInitializer())
                .run(args);
    }

//    public static void start() {
//        SpringApplication.run(ScheduleApplication.class);
//    }

}

//TODO 4.разобраться как с помощью gradle запускать отдельный модуль а не весь проект
