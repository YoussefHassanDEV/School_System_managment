package com.example.student_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Student extends AppUser {

    @Column(nullable = false)
    private String gpa;

    @Column(nullable = false)
    private Integer level;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentSubject> studentSubjects;

}