package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
public class EmployerController extends GenericUserController<EmployerService>{
    public EmployerController(EmployerService employerService) {
        super(employerService);
    }

    @GetMapping
    public List<EmployerDTO> getAllEmployers(){
        logger.info("getAllEmployers");
        return service.findAllEmployers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long id){
        logger.info("getEmployerById");
        return service.findEmployerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEmployer(@RequestBody EmployerDTO employerDTO){
        logger.info("createEmployer");
        service.createEmployer(employerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
