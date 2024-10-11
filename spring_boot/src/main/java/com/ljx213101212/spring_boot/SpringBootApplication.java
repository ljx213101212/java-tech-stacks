package com.ljx213101212.spring_boot;

import org.springframework.boot.SpringApplication;

//If no scanBasePackages,  com.ljx213101212.spring_boot_3_2024 will be the default package.
@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.ljx213101212.spring_boot", "com.ljx213101212.outsider_package_sample"})
public class SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}

}
