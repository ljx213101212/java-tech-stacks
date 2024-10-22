package com.ljx213101212.search_engines_elasticsearch.Task3.config;


import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestClient restClient() {
        String apiKey = System.getenv("ELASTIC_API_KEY"); // Read the API key from environment variables

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ).setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.addInterceptorFirst((HttpRequestInterceptor) (request, context) ->
                        request.addHeader("Authorization", "ApiKey " + apiKey)
                )
        );;
        return builder.build();
    }
}