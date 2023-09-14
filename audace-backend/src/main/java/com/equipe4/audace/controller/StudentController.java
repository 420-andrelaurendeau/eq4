package com.equipe4.audace.controller;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.OfferDTO;
import com.equipe4.audace.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody StudentDTO studentDTO) {
        logger.info("createStudent");
        studentService.createStudent(studentDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId) {
        logger.info("getOffersByDepartment");

        return ResponseEntity.ok(studentService.getOffersByDepartment(departmentId));
    }
}