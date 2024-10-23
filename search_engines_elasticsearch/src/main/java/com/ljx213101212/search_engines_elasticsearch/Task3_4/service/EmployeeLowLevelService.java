package com.ljx213101212.search_engines_elasticsearch.Task3_4.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.Util;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.model.Employee;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Service
public class EmployeeLowLevelService {

    @Autowired
    private RestClient restClient;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    private List<Employee> getEmployeeFromHits (Request request) {
        List<Employee> employees = new ArrayList<>();

        try {
            Response response = restClient.performRequest(request);
            // Deserialize the response
            Map<String, Object> responseBody = objectMapper.readValue(response.getEntity().getContent(), Map.class);
            List<Map<String, Object>> hits = (List<Map<String, Object>>) ((Map<String, Object>) responseBody.get("hits")).get("hits");

            for (Map<String, Object> hit : hits) {
                Map<String, Object> source = (Map<String, Object>) hit.get("_source");
                Employee employee = objectMapper.convertValue(source, Employee.class);
                // Map _id to id
                String id = (String) hit.get("_id");
                employee.setId(id);

                employees.add(employee);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return employees;
    }

    private Double getAggregationMetric(Map<String, Object> responseBody, String field, String metric) {
        if (responseBody == null || field == null || metric == null) {
            throw new IllegalArgumentException("Response body, field, and metric must not be null.");
        }

        try {
            Map<String, Object> aggregations = (Map<String, Object>) responseBody.get("aggregations");
            if (aggregations == null) {
                throw new IllegalArgumentException("Aggregations not found in the response body.");
            }

            Map<String, Object> fieldStats = (Map<String, Object>) aggregations.get(field + "_stats");
            if (fieldStats == null) {
                throw new IllegalArgumentException("Field stats not found for field: " + field);
            }

            return (Double) fieldStats.get(metric);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid response body structure or metric type.", e);
        }
    }


    public List<Employee> getAllEmployees() throws IOException, NoSuchElementException {

        Request request = new Request("GET", "/employees/_search");

        String jsonString = Util.elasticJsonString("elastic/all_employees.json");
        request.setJsonEntity(jsonString);
        List<Employee> employees = getEmployeeFromHits(request);
        return employees;
    }


    public Employee getEmployeeById(String id) throws IOException, NoSuchElementException {


        // Implement code to retrieve an employee by ID
        Employee employee = new Employee();
        Request request = new Request("GET", "/employees/_search?filter_path=hits");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = Util.elasticJsonString("elastic/employee_by_id.json");
        jsonString = jsonString.replaceAll("replace_id", id);
        request.setJsonEntity(jsonString);
        List<Employee> employees = getEmployeeFromHits(request);
        if (employees.isEmpty()) {
            throw new NoSuchElementException("Cannot find Employee: " + id);
        }
        return employees.get(0);
    }

    public String createEmployee(Employee employee) throws IOException {
        // Implement code to create an employee document
        Request request = new Request("POST", "/employees/_doc");
        String employeeJson = objectMapper.writeValueAsString(employee);
        request.setJsonEntity(employeeJson);
        Response response = restClient.performRequest(request);
        // Deserialize the response
        Map<String, Object> responseBody = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return responseBody.get("_id").toString();
    }

    public String deleteEmployee(String id) throws IOException {
        // Implement code to delete an employee document
        Request request = new Request("DELETE", "/employees/_doc/"+ id);
        Response response = restClient.performRequest(request);
        // Deserialize the response
        Map<String, Object> responseBody = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return responseBody.get("_id").toString();
    }

    public List<Employee> searchEmployees(String field, String value) throws IOException {
        // Implement code to search employees by field using match or term query
        Request request = new Request("POST", "/employees/_search?filter_path=hits.hits");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = Util.elasticJsonString("elastic/search_by_field_value.json");
        jsonString = jsonString.replaceAll("replace_field", field);
        jsonString = jsonString.replaceAll("replace_value", value);
        // Setting a query to match all documents
        request.setJsonEntity(jsonString);
        return getEmployeeFromHits(request);
    }

    public Double aggregateEmployees(String field, String metric) throws IOException {
        // Implement code to perform an aggregation (like average of a numeric field)
        Request request = new Request("POST", "/employees/_search?filter_path=aggregations");
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = Util.elasticJsonString("elastic/aggregation_by_field_metric.json");
        jsonString = jsonString.replaceAll("replace_agg_key", field + "_stats");
        jsonString = jsonString.replaceAll("replace_field_name", field);
        request.setJsonEntity(jsonString);
        Response response = restClient.performRequest(request);
        Map<String, Object> responseBody = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return getAggregationMetric(responseBody, field, metric);
    }
}
