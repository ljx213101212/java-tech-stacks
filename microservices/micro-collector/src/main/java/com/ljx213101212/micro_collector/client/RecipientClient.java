package com.ljx213101212.micro_collector.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "recipientClient", url = "${recipient.service.url}")
public interface RecipientClient {
//    @GetMapping
//    List<String> getMessages();

    @GetMapping
    String getMessage();
}