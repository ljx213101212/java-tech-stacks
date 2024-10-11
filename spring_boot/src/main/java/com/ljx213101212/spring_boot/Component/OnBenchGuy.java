package com.ljx213101212.spring_boot.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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

    // Warning: This function won't be called because current bean scope is prototype
    @PostConstruct
    public void doMyStartupStuff() {
        System.out.println("[OnBenchGuy] Now you are on bench, find something to fight for and improve yourself!");
    }

    // Warning: This function won't be called because current bean scope is prototype
    @PreDestroy
    public void doMyCleanupStuff() {
        System.out.println("[OnBenchGuy] Congrats or Sorry to hear about this,  you are either assigned to new project or fired");
    }

    @Override
    public String getDailyWorkout() {
        return "Learn as much as you can!";
    }
}