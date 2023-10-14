package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.security.jwt.TimedJwt;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.service.auth.AuthService;
import com.equipe4.audace.utils.JwtManipulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtManipulator jwtManipulator;
    @MockBean
    private StudentService studentService;
    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private AuthService authService;
    @MockBean
    private CvRepository cvRepository;

    @Test
    @WithMockUser
    public void testLogin_happyPath() throws Exception {
        UserDTO mockedUserDTO = mock(UserDTO.class);
        User mockedUser = mock(User.class);

        when(authService.login(any())).thenReturn(Optional.of(mockedUserDTO));
        when(mockedUserDTO.fromDTO()).thenReturn(mockedUser);
        when(jwtManipulator.generateToken(mockedUser)).thenReturn(mock(TimedJwt.class));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testLogin_UserNotFound_soUnauthorized() throws Exception {
        when(authService.login(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"identification\":\"identification\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testStudentSignup_happyPath() throws Exception {
        String departmentCode = "BINGBONG";

        when(studentService.createStudent(any(), eq(departmentCode))).thenReturn(Optional.of(mock(StudentDTO.class)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup/student/" + departmentCode).with(csrf())
                .content(createJsonOfStudentDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testStudentSignup_InvalidStudent() throws Exception {
        StudentDTO mockedStudentDTO = mock(StudentDTO.class);
        String departmentCode = "BINGBONG";

        when(studentService.createStudent(mockedStudentDTO, departmentCode)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup/student/" + departmentCode).with(csrf())
                .content(createJsonOfStudentDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    private String createJsonOfStudentDTO() {
        return "{" +
                "\"firstName\":\"\"," +
                "\"lastName\":\"\"," +
                "\"email\":\"\"," +
                "\"password\":\"\"," +
                "\"address\":\"\"," +
                "\"phone\":\"\"," +
                "\"studentNumber\":\"\"," +
                "\"cvs\":[]," +
                "\"type\":\"student\"" +
                "}";
    }

    @Test
    @WithMockUser
    public void testEmployerSignup_happyPath() throws Exception {
        when(employerService.createEmployer(any())).thenReturn(Optional.of(mock(EmployerDTO.class)));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup/employer").with(csrf())
                .content(createJsonOfEmployerDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testEmployerSignup_invalidEmployer() throws Exception {
        when(employerService.createEmployer(any())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup/employer").with(csrf())
                .content(createJsonOfEmployerDTO())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    private String createJsonOfEmployerDTO() {
        return "{" +
                "\"firstName\":\"\"," +
                "\"lastName\":\"\"," +
                "\"email\":\"\"," +
                "\"password\":\"\"," +
                "\"address\":\"\"," +
                "\"phone\":\"\"," +
                "\"companyName\":\"\"," +
                "\"companyAddress\":\"\"," +
                "\"companyPhone\":\"\"," +
                "\"companyDescription\":\"\"," +
                "\"offers\":[]," +
                "\"type\":\"employer\"" +
                "}";
    }
}
