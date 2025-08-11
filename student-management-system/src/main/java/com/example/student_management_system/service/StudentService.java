package com.example.student_management_system.service;

import com.example.student_management_system.Enum.Role;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.StudentSubject;
import com.example.student_management_system.model.Subject;
import com.example.student_management_system.repositiory.ExamRepository;
import com.example.student_management_system.repositiory.StudentRepository;
import com.example.student_management_system.repositiory.StudentSubjectRepository;
import com.example.student_management_system.repositiory.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final ExamRepository examRepository;
    private final PasswordEncoder passwordEncoder; // Inject this too

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          SubjectRepository subjectRepository,
                          StudentSubjectRepository studentSubjectRepository,
                          ExamRepository examRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentSubjectRepository = studentSubjectRepository;
        this.examRepository = examRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentSubject addSubjectToStudent(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if already enrolled in 7 subjects
        long subjectCount = studentSubjectRepository.countByStudentId(studentId);
        if (subjectCount >= 7) {
            throw new RuntimeException("Cannot take more than 7 subjects");
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Check if already enrolled in this subject
        if (studentSubjectRepository.existsByStudentAndSubject(student, subject)) {
            throw new RuntimeException("Student already enrolled in this subject");
        }

        StudentSubject studentSubject = StudentSubject.builder()
                .student(student)
                .subject(subject)
                .paymentApproved(false) // default false until approved by dept manager
                .examPassed(false)
                .build();

        return studentSubjectRepository.save(studentSubject);
    }

    public StudentSubject approvePayment(Long studentSubjectId) {
        StudentSubject ss = studentSubjectRepository.findById(studentSubjectId)
                .orElseThrow(() -> new RuntimeException("StudentSubject not found"));
        ss.setPaymentApproved(true);
        return studentSubjectRepository.save(ss);
    }

    public boolean canLevelUp(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        int currentLevel = student.getLevel();

        // Get subjects of this level
        List<Subject> subjectsAtLevel = subjectRepository.findByLevel(currentLevel);

        // Check if all subjects passed
        for (Subject subject : subjectsAtLevel) {
            boolean passed = examRepository.existsByStudentIdAndSubjectIdAndPassedTrue(studentId, subject.getId());
            if (!passed) return false; // failed or no exam passed
        }
        return true;
    }

    public Student promoteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (canLevelUp(studentId)) {
            int newLevel = student.getLevel() + 1;
            if (newLevel > 5) {
                throw new RuntimeException("Student already at max level");
            }
            student.setLevel(newLevel);
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student has not passed all exams");
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        student.setRole(Role.STUDENT);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(updatedStudent.getName());
                    existingStudent.setUsername(updatedStudent.getUsername());
                    existingStudent.setPassword(updatedStudent.getPassword());
                    existingStudent.setGpa(updatedStudent.getGpa());
                    existingStudent.setLevel(updatedStudent.getLevel());
                    return studentRepository.save(existingStudent);
                })
                .orElseGet(() -> {
                    updatedStudent.setId(id);
                    updatedStudent.setRole(Role.STUDENT);
                    return studentRepository.save(updatedStudent);
                });
    }
}
