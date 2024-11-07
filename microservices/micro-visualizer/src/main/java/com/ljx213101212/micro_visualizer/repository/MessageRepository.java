package com.ljx213101212.micro_visualizer.repository;

import com.ljx213101212.micro_visualizer.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
}