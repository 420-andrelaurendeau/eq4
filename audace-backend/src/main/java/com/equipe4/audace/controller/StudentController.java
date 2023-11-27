package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.dto.cv.CvDTO;
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

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId) {
        return service.getStudentById(studentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/offers/{departmentId}/{sessionId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId, @PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getAcceptedOffersByDepartment(departmentId, sessionId));
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
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/applications")
    public ResponseEntity<HttpStatus> createApplication(@RequestBody ApplicationDTO applicationDTO){
        logger.info("createApplication");
        try {
            service.createApplication(applicationDTO);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/appliedOffers/{sessionId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByStudentIdAndSessionId(@RequestParam Long studentId, @PathVariable Long sessionId) {
        logger.info("getApplicationsByStudentIdAndSessionId");

        List<ApplicationDTO> offersList;

        try {
            offersList = service.getApplicationsByStudentIdAndSessionId(studentId, sessionId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(offersList);
    }

    @PostMapping("/sign_contract/{contractId}")
    public ResponseEntity<SignatureDTO> signContract(@PathVariable Long contractId) {
        logger.info("studentSignContract");
        try {
            return ResponseEntity.ok(
                    service.signContract(contractId).orElseThrow()
            );
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/applications/{applicationId}/contract")
    public ResponseEntity<ContractDTO> getContractByApplication(@PathVariable Long applicationId) {
        logger.info("getContractByApplication");
        try {
            return ResponseEntity.ok(service.getContractByApplicationId(applicationId).orElseThrow());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long contractId){
        logger.info("getContractById");
        return service.findContractById(contractId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/contracts/{contractId}/signatures")
    public List<SignatureDTO> getSignaturesByContractId(@PathVariable Long contractId) {
        logger.info("getSignatureByContractId");
        return service.getSignaturesByContractId(contractId);
    }
}