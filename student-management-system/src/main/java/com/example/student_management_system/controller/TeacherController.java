package com.example.student_management_system.controller;

import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private  final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    @GetMapping("/{id}")
    public Optional<Teacher> getTeacherByID(@PathVariable Long id){
        return teacherService.getTeacherById(id);
    }
    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher)
    {
        return teacherService.addTeacher(teacher);
    }
    @DeleteMapping("{id}")
    public void deleteTeacher(@PathVariable long id){
        teacherService.deleteTeacher(id);
    }
    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacher(id, updatedTeacher);
    }

}
