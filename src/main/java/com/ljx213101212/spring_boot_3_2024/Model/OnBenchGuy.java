package com.ljx213101212.spring_boot_3_2024.Model;

import org.springframework.stereotype.Component;

//@Component: In constructor: TennisCoach is not printed because annotation is commented.
//@Component beans are singletons and singletons are instantiated during the startup of the Spring ApplicationContext.
public class TennisCoach implements Coach {

    public TennisCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Practice your backhand volley";
    }
}