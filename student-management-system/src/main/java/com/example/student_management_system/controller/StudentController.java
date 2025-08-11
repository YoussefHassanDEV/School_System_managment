package com.example.student_management_system.controller;

import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.StudentSubject;
import com.example.student_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    @Autowired

    public StudentController(StudentService service) {
        this.service = service;
    }
    @GetMapping
    public List<Student> getAllStudents(){
        return  service.getAllStudents();

    }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id){
        service.deleteStudent(id);
    }
    @GetMapping("hello")
    public String hello(){
        return "<h1>Hello Student<h1/>";
    }
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return service.updateStudent(id, updatedStudent);
    }
    @PostMapping("/{studentId}/subjects/{subjectId}")
    public StudentSubject addSubjectToStudent(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return service.addSubjectToStudent(studentId, subjectId);
    }
    @PostMapping("/{studentId}/promote")
    public Student promoteStudent(@PathVariable Long studentId) {
        return service.promoteStudent(studentId);
    }
}
