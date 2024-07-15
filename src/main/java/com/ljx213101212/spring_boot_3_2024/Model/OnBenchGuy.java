package com.ljx213101212.spring_boot_3_2024.Model;

//@Component: In constructor: OnBenchGuy is not printed because annotation is commented.
//@Component beans are singletons and singletons are instantiated during the startup of the Spring ApplicationContext.
public class OnBenchGuy implements Coach {

    public OnBenchGuy() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Learn as much as you can!";
    }
}