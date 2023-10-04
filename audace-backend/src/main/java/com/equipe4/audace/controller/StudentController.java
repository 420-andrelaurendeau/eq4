package com.equipe4.audace.controller;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping("/signup/{departmentCode}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody StudentDTO studentDTO, @PathVariable String departmentCode) {
        logger.info("createStudent");
        studentService.createStudent(studentDTO, departmentCode);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId) {
        logger.info("getOffersByDepartment");

        List<OfferDTO> offers;

        try {
            offers = studentService.getOffersByDepartment(departmentId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(offers);
    }

    @PostMapping("/upload/{studentId}")
    public ResponseEntity<HttpStatus> uploadCv(@PathVariable Long studentId, @RequestParam("file") MultipartFile file) {
        logger.info("uploadCv");

        try {
            studentService.saveCv(file, studentId);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            if (e.getMessage().equals("Student not found")) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}