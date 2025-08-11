package com.example.student_management_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student taking this subject
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Subject taken
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Payment approved by department manager
    private boolean paymentApproved;

    // Exam status: true if a student passed the exam for this subject
    private boolean examPassed;
}
