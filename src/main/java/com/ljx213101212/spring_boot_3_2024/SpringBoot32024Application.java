package com.ljx213101212.spring_boot_3_2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//If no scanBasePackages,  com.ljx213101212.spring_boot_3_2024 will be the default package.
@SpringBootApplication(scanBasePackages = {"com.ljx213101212.spring_boot_3_2024", "com.ljx213101212.outsider_package_sample"})
public class SpringBoot32024Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot32024Application.class, args);
	}

}
