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
public class EmployerController {
    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }


    @GetMapping
    public List<EmployerDTO> getAllEmployers(){
        return employerService.findAllEmployers();
    }

    @PostMapping
    public ResponseEntity<EmployerDTO> createEmployer(@RequestBody EmployerDTO employerDTO){
        return employerService.saveEmployer(employerDTO)
                .map(employer -> ResponseEntity.status(HttpStatus.OK).body(employerDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
