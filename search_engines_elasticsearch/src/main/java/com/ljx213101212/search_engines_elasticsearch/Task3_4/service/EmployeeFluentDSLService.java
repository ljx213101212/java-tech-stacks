package com.ljx213101212.search_engines_elasticsearch.Task3_4.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ljx213101212.search_engines_elasticsearch.Task3_4.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeFluentDSLService {

    private final ElasticsearchClient client;

    @Autowired
    public EmployeeFluentDSLService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<Employee> getAllEmployeesByFluentDSL() throws Exception {
        try {
            SearchResponse<Employee> response = client.search(s -> s.index("employees").query(q -> q.matchAll(m -> m)), Employee.class);
            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception((e.getMessage()));
        }
    }

    public Employee getEmployeeByIdByFluentDSL(String id) throws Exception {
        try {
            SearchResponse<Employee> response = client.search(s -> s
                    .index("employees")
                    .query(q -> q
                            .ids(i -> i
                                    .values(id)
                            )
                    ), Employee.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String createEmployeeFluentDSL(Employee employee) throws IOException {
        IndexResponse response = client.index(i -> i
                .index("employees")
                .document(employee)
        );
        return response.id();
    }

    public String deleteEmployeeByDSL(String id) throws IOException {
        DeleteResponse response = client.delete(d -> d
                .index("employees")
                .id(id)
        );
        return response.result().toString();
    }

    public List<Employee> searchEmployeesByDSL(String field, String value) throws IOException {
        SearchResponse<Employee> response = client.search(s -> s
                        .index("employees")
                        .query(q -> q
                                .match(m -> m
                                        .field(field)
                                        .query(value)
                                )
                        ),
                Employee.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public Double aggregateEmployeesByDSL(String field, String metric) throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("employees")
                        .aggregations(metric, a -> {
                            switch (metric.toLowerCase()) {
                                case "avg":
                                    return a.avg(avg -> avg.field(field));
                                case "sum":
                                    return a.sum(sum -> sum.field(field));
                                case "min":
                                    return a.min(min -> min.field(field));
                                case "max":
                                    return a.max(max -> max.field(field));
                                default:
                                    throw new IllegalArgumentException("Unsupported metric: " + metric);
                            }
                        }),
                Void.class
        );

        // Extract the metric result
        switch (metric.toLowerCase()) {
            case "avg":
                return response.aggregations().get(metric).avg().value();
            case "sum":
                return response.aggregations().get(metric).sum().value();
            case "min":
                return response.aggregations().get(metric).min().value();
            case "max":
                return response.aggregations().get(metric).max().value();
            default:
                throw new IllegalArgumentException("Unsupported metric: " + metric);
        }
    }
}
