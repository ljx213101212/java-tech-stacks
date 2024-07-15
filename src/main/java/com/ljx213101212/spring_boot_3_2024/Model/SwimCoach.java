package com.ljx213101212.spring_boot_3_2024.Model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class SwimCoach implements Coach {

    public SwimCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Swim 1000 meters as a warm up";
    }
}
