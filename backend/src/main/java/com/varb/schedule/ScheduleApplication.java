package com.varb.schedule;

import com.varb.schedule.config.RootDirInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ScheduleApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(ScheduleApplication.class)
                .initializers(new RootDirInitializer())
                .run(args);
    }

}

//TODO 4.разобраться как с помощью gradle запускать отдельный модуль а не весь проект
