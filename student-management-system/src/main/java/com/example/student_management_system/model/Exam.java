package com.example.student_management_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The student taking the exam
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // The subject of the exam
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // The teacher who created/administered the exam
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Date and time of exam
    private LocalDateTime examDate;

    // Result of exam: passed or not
    private boolean passed;
    private Double score;

}
