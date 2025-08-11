package com.example.student_management_system.service;

import com.example.student_management_system.Enum.Role;
import com.example.student_management_system.Exceptions.ResourceNotFoundException;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repositiory.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher addTeacher(Teacher teacher) {
        teacher.setRole(Role.TEACHER);  // Make sure Role.TEACHER exists in your Enum
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher with id " + id + " not found");
        }
        teacherRepository.deleteById(id);
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    existingTeacher.setName(updatedTeacher.getName());
                    return teacherRepository.save(existingTeacher);
                })
                .orElseGet(() -> {
                    updatedTeacher.setId(id);
                    return teacherRepository.save(updatedTeacher);
                });
    }
}
