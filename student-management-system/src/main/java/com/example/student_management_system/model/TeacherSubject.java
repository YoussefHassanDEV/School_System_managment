package com.example.student_management_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(nullable = false)
    private Integer level; // 1 to 5
}
