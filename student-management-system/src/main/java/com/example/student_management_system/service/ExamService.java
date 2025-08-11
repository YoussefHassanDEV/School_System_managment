package com.example.student_management_system.service;

import com.example.student_management_system.model.*;
import com.example.student_management_system.repositiory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;

    @Autowired
    public ExamService(ExamRepository examRepository,
                       StudentRepository studentRepository,
                       SubjectRepository subjectRepository,
                       TeacherRepository teacherRepository,
                       StudentSubjectRepository studentSubjectRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentSubjectRepository = studentSubjectRepository;
    }

    // Create a new exam result for a student-subject
    public Exam createExam(Long studentId, Long subjectId, Long teacherId, boolean passed, Double score) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Exam exam = Exam.builder()
                .student(student)
                .subject(subject)
                .teacher(teacher)
                .examDate(LocalDateTime.now())
                .passed(passed)
                .score(score)
                .build();

        Exam savedExam = examRepository.save(exam);

        // If passed, update StudentSubject.examPassed
        if (passed) {
            StudentSubject ss = studentSubjectRepository.findByStudentAndSubject(student, subject)
                    .orElseThrow(() -> new RuntimeException("StudentSubject not found"));
            ss.setExamPassed(true);
            studentSubjectRepository.save(ss);
        }

        return savedExam;
    }

    public List<Exam> getExamsForStudentSubject(Long studentId, Long subjectId) {
        return examRepository.findByStudentIdAndSubjectId(studentId, subjectId);
    }
}
