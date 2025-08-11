package com.example.student_management_system.repositiory;

import com.example.student_management_system.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    boolean existsByStudentIdAndSubjectIdAndPassedTrue(Long studentId, Long subjectId);

    List<Exam> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
