package com.ljx213101212.spring_boot.Component;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
//The Constructor will only be created on demand.
@Lazy
public class LazyGuy implements  Coach{

    public LazyGuy() {
        System.out.println("You won't see me during application startup, because I am @Lazy: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Nah, I wanna take a rest!";
    }
}
