package com.ljx213101212.liquibase.repository;


import com.ljx213101212.liquibase.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository  extends JpaRepository<Country, Long> {
}
