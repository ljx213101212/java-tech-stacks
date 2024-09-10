package com.ljx213101212.kafka_stream.task1.config;


public class Constants {

    private String inputTopicName;

    public String getInputTopicName() {
        return inputTopicName;
    }

    public void setInputTopicName(String inputTopicName) {
        this.inputTopicName = inputTopicName;
    }

    public String getOutputTopicName() {
        return outputTopicName;
    }

    public void setOutputTopicName(String outputTopicName) {
        this.outputTopicName = outputTopicName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    private String outputTopicName;

    private String applicationId;

    private String bootstrapServer;
}
