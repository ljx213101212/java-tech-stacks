//package com.ljx213101212.liquibase.config;
//
//
//import liquibase.integration.spring.SpringLiquibase;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class LiquibaseConfig {
//
//    @Value("${spring.liquibase.rollback:false}") // Custom property for rollback configuration
//    private boolean rollback;
//
//    @Bean
//    public SpringLiquibase liquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
//
//        // If rollback is enabled, perform rollback on startup
//        if (rollback) {
//            liquibase.setRollbackFile("classpath:db/changelog/V2__insert_init_data.yaml");  // Rollback change log file
//        }
//
//        return liquibase;
//    }
//}
//
