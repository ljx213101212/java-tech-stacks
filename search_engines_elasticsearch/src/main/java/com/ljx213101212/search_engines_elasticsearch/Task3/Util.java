package com.ljx213101212.search_engines_elasticsearch.Task3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static String elasticJsonString(String queryFileName) throws IOException {
        // Create an ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(queryFileName);
        Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);

        return mapper.writeValueAsString(jsonMap);
    }
}
