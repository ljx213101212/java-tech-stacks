package com.ljx213101212.liquibase.repository;

import com.ljx213101212.liquibase.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
