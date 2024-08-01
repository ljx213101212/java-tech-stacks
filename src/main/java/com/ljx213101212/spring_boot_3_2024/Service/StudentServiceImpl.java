package com.ljx213101212.spring_boot_3_2024.Service;

import com.ljx213101212.spring_boot_3_2024.Dao.StudentDao;
import com.ljx213101212.spring_boot_3_2024.Enitity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao theStudentDAO) {
        studentDao = theStudentDAO;
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findById(int theId) {
        return studentDao.findById(theId);
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        return studentDao.findByLastName(lastName);
    }

    @Override
    @Transactional
    //@Transactional for write access
    public void save(Student student) {
        studentDao.save(student);
    }

    @Override
    public void update(Student student) {
       studentDao.update((student));
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        studentDao.delete(theId);
    }

    @Override
    @Transactional
    public int deleteAll() {
        return studentDao.deleteAll();
    }
}
