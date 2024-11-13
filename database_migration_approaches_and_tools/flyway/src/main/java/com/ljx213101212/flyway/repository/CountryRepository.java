package com.ljx213101212.flyway.repository;

import com.ljx213101212.flyway.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository  extends JpaRepository<Country, Long> {
}
