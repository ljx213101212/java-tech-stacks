package com.ljx213101212.spring_boot_3_2024.Rest;

import com.ljx213101212.spring_boot_3_2024.Model.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorInjectionController {

    // define a private field for the dependency
    private Coach myCoach;


    // Qualifier: make the class name first character as lower case.
    public ConstructorInjectionController(@Qualifier("swimCoach") Coach theCoach) {
        System.out.println("In constructor: " + getClass().getSimpleName());
        myCoach = theCoach;
    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }
}
