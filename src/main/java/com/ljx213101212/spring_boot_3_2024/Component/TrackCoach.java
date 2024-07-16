package com.ljx213101212.spring_boot_3_2024.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class TrackCoach implements Coach {

    public TrackCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @PostConstruct
    public void doMyStartupStuff() {
        System.out.println("[Track Coach] YAY");
    }

    // define our destroy method
    @PreDestroy
    public void doMyCleanupStuff() {
        System.out.println("[Track Coach] Bye!");
    }
    @Override
    public String getDailyWorkout() {
        return "Run a hard 5k!";
    }
}
