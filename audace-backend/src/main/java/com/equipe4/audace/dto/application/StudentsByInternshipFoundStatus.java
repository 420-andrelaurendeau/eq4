package com.equipe4.audace.dto.application;

import com.equipe4.audace.dto.StudentDTO;

import java.util.List;

public record StudentsByInternshipFoundStatus(
        List<StudentDTO> studentsWithInternship,
        List<StudentDTO> studentsWithAcceptedResponse,
        List<StudentDTO> studentsWithPendingResponse,
        List<StudentDTO> studentsWithRefusedResponse,
        List<StudentDTO> studentsWithoutApplications
) {}
