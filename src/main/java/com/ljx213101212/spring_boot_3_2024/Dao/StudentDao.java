package com.ljx213101212.spring_boot_3_2024.Dao;

import com.ljx213101212.spring_boot_3_2024.Enitity.Student;

import java.util.List;

public interface StudentDao {
    void save(Student theStudent);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String theLastName);

    void update(Student theStudent);

    void delete(Integer id);

    int deleteAll();
}
