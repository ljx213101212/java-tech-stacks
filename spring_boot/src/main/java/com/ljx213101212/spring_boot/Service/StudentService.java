package com.ljx213101212.spring_boot.Service;

import com.ljx213101212.spring_boot.Enitity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(int theId);

    List<Student> findByLastName(String theName);

    void save(Student student);

    void update(Student student);

    void deleteById(int theId);

    int deleteAll();
}
