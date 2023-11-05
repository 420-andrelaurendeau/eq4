package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.notification.NotificationDTO;
import com.equipe4.audace.dto.notification.NotificationOfferDTO;
import com.equipe4.audace.dto.session.SessionDTO;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.notification.NotificationRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.SessionRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import com.equipe4.audace.service.UserService;
import com.equipe4.audace.utils.JwtManipulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtManipulator jwtManipulator;

    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployerRepository employerRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private ManagerRepository managerRepository;
    @MockBean
    private CvRepository cvRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private SaltRepository saltRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private OfferSessionRepository offerSessionRepository;
    @MockBean
    private ApplicationRepository applicationRepository;
    @MockBean
    private OfferRepository offerRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private StudentService studentService;

    @Test
    @WithMockUser(username = "user")
    void testGetUser() throws Exception {
        long userId = 1L;
        UserDTO userDTO = new EmployerDTO(userId, "peterson", "sara", "lesun@live.com", "password", "RocaFella Records", "artist", "3 York St", "4387253892", "slat");
        Optional<UserDTO> userOptional = Optional.of(userDTO);
        when(userService.getUser(userId)).thenReturn(userOptional);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDTO.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$.address", is(userDTO.getAddress())))
                .andExpect(jsonPath("$.phone", is(userDTO.getPhone())));
    }

    @Test
    @WithMockUser(username = "user")
    void testGetUserNotFound() throws Exception {
        when(userService.getUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetSessions() throws Exception {
        mockMvc.perform(get("/users/sessions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetCurrentSession() throws Exception {
        SessionDTO sessionDTO = mock(SessionDTO.class);
        when(userService.getCurrentSession()).thenReturn(Optional.of(sessionDTO));

        mockMvc.perform(get("/users/sessions/current"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetCurrentSessionNotFound() throws Exception {
        when(userService.getCurrentSession()).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/sessions/current"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetSession() throws Exception {
        SessionDTO sessionDTO = mock(SessionDTO.class);
        when(userService.getSessionById(1L)).thenReturn(Optional.of(sessionDTO));

        mockMvc.perform(get("/users/sessions/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetSessionNotFound() throws Exception {
        when(userService.getSessionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/sessions/{id}", 1L))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "user")
    void testGetAllNotificationsByUserId() throws Exception {
        NotificationDTO notificationDTO = mock(NotificationOfferDTO.class);
        List<NotificationDTO> notificationDTOs = new ArrayList<>();
        notificationDTOs.add(notificationDTO);
        when(userService.getAllNotificationByUserId(1L)).thenReturn(notificationDTOs);
        mockMvc.perform(get("/users/notifications/1"))
                .andExpect(status().isOk());
    }
}
