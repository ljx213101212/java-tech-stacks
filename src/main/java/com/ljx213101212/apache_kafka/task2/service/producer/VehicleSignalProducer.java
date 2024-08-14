package com.ljx213101212.apache_kafka.task2.service.producer;

import com.ljx213101212.apache_kafka.task2.model.VehicleSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljx213101212.apache_kafka.task2.config.Constants.INPUT_TOPIC;


@Service
public class VehicleSignalProducer {

    private static final Logger log = LoggerFactory
            .getLogger(VehicleSignalProducer.class);

    @Autowired
    private KafkaTemplate<String, VehicleSignal> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void produceSignal(VehicleSignal signal) throws InterruptedException{
        kafkaTemplate.send(INPUT_TOPIC, signal.getVehicleId(), signal);
        log.info("[INPUT_TOPIC] - Producer - " + INPUT_TOPIC + ": vehicle id: " + signal.getVehicleId());
        kafkaTemplate.flush();
    }

}
