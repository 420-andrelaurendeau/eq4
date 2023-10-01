package com.equipe4.audace.controller;

import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController extends GenericUserController<Student, StudentService> {
    public StudentController(StudentService studentService) {
        super(studentService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return service.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/signup/{departmentCode}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody StudentDTO studentDTO, @PathVariable String departmentCode) {
        logger.info("createStudent");
        service.createStudent(studentDTO, departmentCode);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartmentMapped(@PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getAcceptedOffersByDepartment(departmentId));
    }
}