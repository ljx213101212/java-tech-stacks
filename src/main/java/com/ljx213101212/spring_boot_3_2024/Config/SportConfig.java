package com.ljx213101212.spring_boot_3_2024.Config;

import com.ljx213101212.spring_boot_3_2024.Component.Coach;
import com.ljx213101212.spring_boot_3_2024.Component.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean("aquatic")
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
