package com.ljx213101212.spring_boot_3_2024.Model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component: In constructor: OnBenchGuy is not printed because annotation is commented.
//@Component beans are singletons and singletons are instantiated during the startup of the Spring ApplicationContext.

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OnBenchGuy implements Coach {

    public OnBenchGuy() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Learn as much as you can!";
    }
}