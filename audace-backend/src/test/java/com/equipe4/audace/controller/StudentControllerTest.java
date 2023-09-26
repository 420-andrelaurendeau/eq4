package com.equipe4.audace.controller;

import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private CvRepository cvRepository;

    @Test
    void uploadCv_happyPath() throws Exception {
        MockMultipartFile file = createMockFile();
        when(studentService.saveCv(file, 1L)).thenReturn(Optional.of(mock(CvDTO.class)));

        mockMvc.perform(
                multipart("/students/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());
    }

    @Test
    void uploadCv_noFile() throws Exception {
        mockMvc.perform(
                multipart("/students/upload/1")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isBadRequest());
    }

    private MockMultipartFile createMockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }
}
