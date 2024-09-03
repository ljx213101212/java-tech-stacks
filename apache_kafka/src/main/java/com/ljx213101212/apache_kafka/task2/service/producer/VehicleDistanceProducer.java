package com.ljx213101212.apache_kafka.task2.service.producer;

import com.ljx213101212.apache_kafka.task2.model.VehicleRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljx213101212.apache_kafka.task2.config.Constants.OUTPUT_TOPIC;

@Service
public class VehicleDistanceProducer {

    private static final Logger log = LoggerFactory
            .getLogger(VehicleSignalProducer.class);

    @Autowired
    private KafkaTemplate<String, Double> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void produceDistance(VehicleRecord record) throws InterruptedException{
        kafkaTemplate.send(OUTPUT_TOPIC, record.getVehicleId(), record.getTotalDistance());
        log.info("[OUT_TOPIC] Producer - " + OUTPUT_TOPIC + ": vehicle id -> total distance: " + record.getTotalDistance());
        kafkaTemplate.flush();
    }
}
