package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.ApplicationDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @GetMapping("/cvs/{studentId}")
    public ResponseEntity<List<CvDTO>> getCvsByStudent(@PathVariable Long studentId) {
        logger.info("getCvsByStudent");

        try {
            List<CvDTO> cvDTOs = service.getCvsByStudent(studentId);
            return ResponseEntity.ok(cvDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/applications")
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO){
        logger.info("createOffer");
        return service.createApplication(applicationDTO).map(application -> ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("{studentId}/appliedOffers")
    public ResponseEntity<List<OfferDTO>> getOffersStudentApplied(@PathVariable Long studentId) {
        logger.info("getOffersStudentApplied");

        List<OfferDTO> offersList;

        try {
            offersList = service.getOffersStudentApplied(studentId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(offersList);
    }
}