package com.ljx213101212.spring_boot_3_2024.Rest;

import com.ljx213101212.spring_boot_3_2024.Model.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringCoreController {

    // define a private field for the dependency
    private final Coach swimCoach;
    private Coach trackCoach;
    //Field Injection

    @Autowired
    private Coach baseballCoach;


    // @Qualifier: make the class name first character as lower case.
    // @Autowired: allows fields to be final
    @Autowired
    public SpringCoreController(@Qualifier("swimCoach") Coach theCoach) {
        System.out.println("In constructor: " + getClass().getSimpleName());
        swimCoach = theCoach;
    }


    //Setter Injection
    @Autowired
    public void setCoach(@Qualifier("trackCoach")Coach theCoach) {
        trackCoach = theCoach;
    }

    //Constructor Injection Mapping Sample
    @GetMapping("/ci/workout")
    public String getDailyWorkout() {
        return swimCoach.getDailyWorkout();
    }

    //Setter Injection Mapping Sample
    @GetMapping("/si/workout")
    public String getTrackWorkout() {
        return trackCoach.getDailyWorkout();
    }

    //Field Injection Mapping Sample
    @GetMapping("/fi/workout")
    public String getBaseballWorkout() {
        return baseballCoach.getDailyWorkout();
    }

}
