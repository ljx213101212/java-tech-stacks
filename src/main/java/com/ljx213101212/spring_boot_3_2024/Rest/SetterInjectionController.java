package com.ljx213101212.spring_boot_3_2024.Rest;

import com.ljx213101212.spring_boot_3_2024.Model.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SetterInjectionController {

    private Coach coach;

    //Field Injection
    @Autowired
    private Coach baseballCoach;

    //Setter Injection
    @Autowired
    public void setCoach(@Qualifier("trackCoach")Coach theCoach) {
        coach = theCoach;
    }

    @GetMapping("/si/workout")
    public String getDailyWorkout() {
        return coach.getDailyWorkout();
    }

    @GetMapping("/fi/workout")
    public String getBaseballWorkout() {
        return baseballCoach.getDailyWorkout();
    }
}
