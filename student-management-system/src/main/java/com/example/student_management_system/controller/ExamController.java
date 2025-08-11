package com.example.student_management_system.controller;

import com.example.student_management_system.model.Exam;
import com.example.student_management_system.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }
    @PostMapping("/create")
    public Exam createExam(@RequestParam Long studentId,
                           @RequestParam Long subjectId,
                           @RequestParam Long teacherId,
                           @RequestParam boolean passed,
                           @RequestParam Double score) {
        return examService.createExam(studentId, subjectId, teacherId, passed, score);
    }


    @GetMapping
    public List<Exam> getExams(@RequestParam Long studentId, @RequestParam Long subjectId) {
        return examService.getExamsForStudentSubject(studentId, subjectId);
    }
}
