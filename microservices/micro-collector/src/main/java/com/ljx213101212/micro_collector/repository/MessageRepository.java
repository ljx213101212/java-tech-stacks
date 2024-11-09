package com.ljx213101212.micro_collector.repository;

import com.ljx213101212.micro_collector.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}