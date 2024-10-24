package com.ljx213101212.search_engines_elasticsearch.Task3_4.controller;

import com.ljx213101212.search_engines_elasticsearch.Task3_4.model.Employee;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.service.EmployeeLowLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeLowLevelController {

    @Autowired
    private EmployeeLowLevelService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
       return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) throws IOException {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String field,
            @RequestParam String value) throws IOException {
        return ResponseEntity.ok(employeeService.searchEmployees(field, value));
    }

    @GetMapping("/aggregate")
    public ResponseEntity<Double> aggregateEmployees(
            @RequestParam String field,
            @RequestParam String metric) throws IOException {
        return ResponseEntity.ok(employeeService.aggregateEmployees(field, metric));
    }
}
