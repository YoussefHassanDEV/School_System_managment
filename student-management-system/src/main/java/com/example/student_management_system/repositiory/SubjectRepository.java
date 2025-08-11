package com.example.student_management_system.repositiory;

import com.example.student_management_system.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByLevel(int level);  // useful for filtering subjects by level
}
