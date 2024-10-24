package com.ljx213101212.search_engines_elasticsearch.Task3_4.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.esql.jdbc.ResultSetEsqlAdapter;
import co.elastic.clients.elasticsearch._helpers.esql.objects.ObjectsEsqlAdapter;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.transport.endpoints.BinaryResponse;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeESQLService {

    private final ElasticsearchClient client;

    @Autowired
    public EmployeeESQLService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<Employee> getAllEmployeesByESQL() throws Exception {
        String query =
                """
                    from employees
                """;
        try {
            // Execute the ESQL query
            return (List<Employee>)client.esql().query(ObjectsEsqlAdapter.of(Employee.class), query);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception((e.getMessage()));
        }
    }

    //https://www.elastic.co/guide/en/elasticsearch/reference/current/esql-metadata-fields.html
    public Employee getEmployeeByIdByESQL(String id) throws Exception {
        String query = String.format("""
            from employees METADATA _id
            | where _id == "%s"
        """, id);

        try {
            // Execute the ESQL query using ObjectsEsqlAdapter
            List<Employee> employees = (List<Employee>) client.esql().query(ObjectsEsqlAdapter.of(Employee.class), query);

            // Return the first employee if present
            return employees.isEmpty() ? null : employees.get(0);

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public List<Employee> searchEmployeesByESQL(String field, String value) throws Exception {
        String query = String.format("""
            from employees
            | where %s == "%s"
        """, field, value);

        try {
            return (List<Employee>) client.esql().query(ObjectsEsqlAdapter.of(Employee.class), query);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public Double aggregateEmployeesByESQL(String field, String metric) throws Exception {
        String query = String.format("""
            from employees
            | STATS %s(%s)
        """, metric.toLowerCase(), field);

        try {
            BinaryResponse response =  client.esql().query(q -> q
                    .format("csv")
                    .delimiter(",")
                    .query(query));

            String result = new BufferedReader(new InputStreamReader(response.content()))
                    .lines().skip(1).collect(Collectors.joining("\n"));

            return  Double.parseDouble(result);

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
