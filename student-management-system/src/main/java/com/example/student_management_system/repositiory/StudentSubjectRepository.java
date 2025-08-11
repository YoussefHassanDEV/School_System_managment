package com.example.student_management_system.repositiory;

import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Subject;
import com.example.student_management_system.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    Optional<StudentSubject> findByStudentAndSubject(Student student, Subject subject);

    long countByStudentId(Long studentId);

    boolean existsByStudentAndSubject(Student student, Subject subject);
}
