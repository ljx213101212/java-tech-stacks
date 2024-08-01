package com.ljx213101212.spring_boot_3_2024.Rest;

import com.ljx213101212.spring_boot_3_2024.Enitity.Student;
import com.ljx213101212.spring_boot_3_2024.Service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HibernateJPAController {
    //CRUD Sample

    private final StudentService studentService;

    HibernateJPAController(StudentService theStudentService) {
        studentService = theStudentService;
    }

    //C
    //Note: For mapping name convention, don't use verb like: create / delete (use HTTP Method as a standard way to verify the nature of the API call)
    @PostMapping("students")
    public void jpaCreate(@RequestBody Student student) {
        // create the student object
        // save the student object
        System.out.println("Saving the student ...");
        studentService.save(student);

        // display id of the saved student
        System.out.println("Saved student. Generated id: " + student.getId());
    }


    @PostMapping("students/list")
    public void jpaCreateMultiple(@RequestBody List<Student> students) {

        for (Student student: students) {
            // save the student objects
            studentService.save(student);
        }
    }

    //R
    @GetMapping("students/{studentId}")
    public Student jpaRead(@PathVariable int studentId) {

        Student myStudent = studentService.findById(studentId);

        // display student
        System.out.println("Found the student: " + myStudent);
        return myStudent;
    }

    @GetMapping("students/all")
    public List<Student> jpaReadAll() {
                // get a list of students
        List<Student> theStudents = studentService.findAll();

        // display list of students
        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }

        return theStudents;
    }

    @GetMapping("students/lastname/{lastname}")
    public List<Student> jpaReadByName(@PathVariable String lastname) {
        // get a list of students
        List<Student> theStudents = studentService.findByLastName(lastname);

        // display list of students
        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
        return theStudents;
    }

    //U
    @PutMapping("students")
    public void jpaUpdate(@RequestBody Student student) {
        // update the student
        studentService.update(student);
    }


    //D
    @DeleteMapping("students/{studentId}")
    public void jpaDelete(@PathVariable int studentId) {
        System.out.println("Deleting student id: " + studentId);
        studentService.deleteById(studentId);
    }

    @DeleteMapping("students/all")
    public void jpaDeleteAll() {
        System.out.println("Deleting all students");
        int numRowsDeleted = studentService.deleteAll();
        System.out.println("Deleted row count: " + numRowsDeleted);
    }
}
