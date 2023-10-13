package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController extends GenericUserController<Student, StudentService> {
    public StudentController(StudentService studentService) {
        super(studentService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return service.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getAcceptedOffersByDepartment(departmentId));
    }

    @PostMapping("/upload/{studentId}")
    public ResponseEntity<HttpStatus> uploadCv(@PathVariable Long studentId, @RequestParam("file") MultipartFile file) {
        logger.info("uploadCv");

        try {
            service.saveCv(file, studentId);
        } catch (NoSuchElementException e) {
            logger.info(e.getMessage());
            if (e.getMessage().equals("Student not found")) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}