package com.ljx213101212.spring_boot_3_2024.Rest;

import com.ljx213101212.spring_boot_3_2024.Dao.StudentDao;
import com.ljx213101212.spring_boot_3_2024.Enitity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HibernateJPAController {
    //CRUD Sample

    private final StudentDao studentDao;

    HibernateJPAController(StudentDao theStudentDao) {
        studentDao = theStudentDao;
    }

    //C
    @GetMapping("jpa-create")
    public void jpaCreate() {
        // create the student object
        System.out.println("Creating new student object ...");
        Student tempStudent = new Student("Paul", "Doe", "paul@luv2code.com");

        // save the student object
        System.out.println("Saving the student ...");
        studentDao.save(tempStudent);

        // display id of the saved student
        System.out.println("Saved student. Generated id: " + tempStudent.getId());
    }

    @GetMapping("jpa-create-multiple")
    public void jpaCreateMultiple() {
        // create multiple students
        System.out.println("Creating 3 student objects ...");
        Student tempStudent1 = new Student("John", "Doe", "john@luv2code.com");
        Student tempStudent2 = new Student("Mary", "Public", "mary@luv2code.com");
        Student tempStudent3 = new Student("Bonita", "Applebum", "bonita@luv2code.com");

        // save the student objects
        System.out.println("Saving the students ...");
        studentDao.save(tempStudent1);
        studentDao.save(tempStudent2);
        studentDao.save(tempStudent3);
    }

    //R
    @GetMapping("jpa-read")
    public void jpaRead() {
        // create  a student object
        System.out.println("Creating new student object ...");
        Student tempStudent = new Student("Daffy", "Duck", "daffy@luv2code.com");

        // save the student
        System.out.println("Saving the student ...");
        studentDao.save(tempStudent);

        // display id of the saved student
        int theId = tempStudent.getId();
        System.out.println("Saved student. Generated id: " + theId);

        // retrieve student based on the id: primary key
        System.out.println("Retrieving student with id: " + theId);
        Student myStudent = studentDao.findById(theId);

        // display student
        System.out.println("Found the student: " + myStudent);
    }

    @GetMapping("jpa-read-all")
    public void jpaReadAll() {
                // get a list of students
        List<Student> theStudents = studentDao.findAll();

        // display list of students
        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
    }

    @GetMapping("jpa-read-by-name")
    public void jpaReadByName() {
        // get a list of students
        List<Student> theStudents = studentDao.findByLastName("Doe");

        // display list of students
        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
    }

    //U
    @GetMapping("jpa-update")
    public void jpaUpdate() {
        // retrieve student based on the id: primary key
        int studentId = 1;
        System.out.println("Getting student with id: " + studentId);
        Student myStudent = studentDao.findById(studentId);

        // change first name to "John"
        System.out.println("Updating student ...");
        myStudent.setFirstName("John");

        // update the student
        studentDao.update(myStudent);

        // display the updated student
        System.out.println("Updated student: " + myStudent);
    }


    //D
    @GetMapping("jpa-delete")
    public void jpaDelete() {
        int studentId = 3;
        System.out.println("Deleting student id: " + studentId);
        studentDao.delete(studentId);
    }

    @GetMapping("jpa-delete-all")
    public void jpaDeleteAll() {
        System.out.println("Deleting all students");
        int numRowsDeleted = studentDao.deleteAll();
        System.out.println("Deleted row count: " + numRowsDeleted);
    }
}
