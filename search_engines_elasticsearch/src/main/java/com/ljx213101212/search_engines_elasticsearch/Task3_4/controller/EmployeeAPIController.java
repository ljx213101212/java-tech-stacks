package com.ljx213101212.search_engines_elasticsearch.Task3_4.controller;

import com.ljx213101212.search_engines_elasticsearch.Task3_4.model.Employee;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.service.EmployeeESQLService;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.service.EmployeeFluentDSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeAPIController {

    @Autowired
    private EmployeeESQLService employeeESQLService;

    @Autowired
    private EmployeeFluentDSLService employeeFluentDSLService;

    //ESQL and Fluent DSL are different ways to CRUD the index
    @GetMapping("/esql/all")
    public ResponseEntity<List<Employee>> getAllEmployeesByESQL() throws Exception {
        return ResponseEntity.ok(employeeESQLService.getAllEmployeesByESQL());
    }

    @GetMapping("/dsl/all")
    public ResponseEntity<List<Employee>> getAllEmployeesByDSL() throws Exception {
        return ResponseEntity.ok(employeeFluentDSLService.getAllEmployeesByFluentDSL());
    }

    @GetMapping("/esql/{id}")
    public ResponseEntity<Employee> getEmployeeByIdByESQL(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(employeeESQLService.getEmployeeByIdByESQL(id));
    }

    @GetMapping("/dsl/{id}")
    public ResponseEntity<Employee> getEmployeeByIdByDSL(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(employeeFluentDSLService.getEmployeeByIdByFluentDSL(id));
    }

    //ES|QL does not support data manipulation
    @PostMapping("/dsl/create")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) throws IOException {
        return ResponseEntity.ok(employeeFluentDSLService.createEmployeeFluentDSL(employee));
    }

    //ES|QL does not support data manipulation
    @DeleteMapping("/dsl/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(employeeFluentDSLService.deleteEmployeeByDSL(id));
    }

    @GetMapping("/dsl/search")
    public ResponseEntity<List<Employee>> searchEmployeesByDSL(
            @RequestParam String field,
            @RequestParam String value) throws IOException {
        return ResponseEntity.ok(employeeFluentDSLService.searchEmployeesByDSL(field, value));
    }

    @GetMapping("/esql/search")
    public ResponseEntity<List<Employee>> searchEmployeesByESQL(
            @RequestParam String field,
            @RequestParam String value) throws Exception {
        return ResponseEntity.ok(employeeESQLService.searchEmployeesByESQL(field, value));
    }

    @GetMapping("/dsl/aggregate")
    public ResponseEntity<Double> aggregateEmployeesByDSL(
            @RequestParam String field,
            @RequestParam String metric) throws IOException {
        return ResponseEntity.ok(employeeFluentDSLService.aggregateEmployeesByDSL(field, metric));
    }

    @GetMapping("/esql/aggregate")
    public ResponseEntity<Double> aggregateEmployeesByESQL(
            @RequestParam String field,
            @RequestParam String metric) throws Exception {
        return ResponseEntity.ok(employeeESQLService.aggregateEmployeesByESQL(field, metric));
    }
}
