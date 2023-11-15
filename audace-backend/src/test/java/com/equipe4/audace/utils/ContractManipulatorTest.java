package com.equipe4.audace.utils;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.*;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContractManipulatorTest {
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ContractManipulator contractManipulator;
    @Test
    public void getContractByApplicationId_HappyPath() {
        Long applicationId = 1L;
        Application application = mock(Application.class);
        Contract contract = mock(Contract.class);
        ContractDTO contractDTO = mock(ContractDTO.class);

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(contractRepository.findByApplication(application)).thenReturn(Optional.of(contract));
        when(contract.toDTO()).thenReturn(contractDTO);

        Optional<ContractDTO> result = contractManipulator.getContractByApplicationId(applicationId);

        assertThat(result).isPresent();
        verify(applicationRepository).findById(applicationId);
        verify(contractRepository).findByApplication(application);
    }

    @Test
    public void getContractByApplicationId_ApplicationNotFound() {
        Long applicationId = 1L;
        when(applicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractManipulator.getContractByApplicationId(applicationId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Application not found");
    }

//    @Test
//    public void signContract_Student() {
//        Long studentId = 1L;
//        Long contractId = 1L;
//        Student mockStudent = mock(Student.class);
//        Contract mockContract = mock(Contract.class);
//        ContractDTO mockContractDTO = mock(ContractDTO.class);
//
//        LocalDate signatureDate = LocalDate.now();
//        Signature<Student> expectedSignature = new Signature<Student>(mockStudent, signatureDate);
//
//        when(userRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
//        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
//        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
//        when(mockContract.toDTO()).thenReturn(mockContractDTO);
//
//        Optional<ContractDTO> result = contractManipulator.signContract(studentId, contractId);
//
//        assertThat(result).isPresent();
//        verify(mockContract).setStudentSignature(expectedSignature);
//        verify(contractRepository).save(mockContract);
//    }


//    @Test
//    public void signContract_Employer() {
//        Long employerId = 1L;
//        Long contractId = 1L;
//        Employer mockEmployer = mock(Employer.class);
//        Contract mockContract = mock(Contract.class);
//        ContractDTO mockContractDTO = mock(ContractDTO.class);
//
//        LocalDate signatureDate = LocalDate.now();
//        Signature<Employer> expectedSignature = new Signature<>(mockEmployer, signatureDate);
//
//        when(userRepository.findById(employerId)).thenReturn(Optional.of(mockEmployer));
//        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
//        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
//        when(mockContract.toDTO()).thenReturn(mockContractDTO);
//
//        Optional<ContractDTO> result = contractManipulator.signContract(employerId, contractId);
//
//        assertThat(result).isPresent();
//        verify(mockContract).setEmployerSignature(expectedSignature);
//        verify(contractRepository).save(mockContract);
//    }

//    @Test
//    public void signContract_Manager() {
//        Long managerId = 1L;
//        Long contractId = 1L;
//        Manager mockManager = mock(Manager.class);
//        Contract mockContract = mock(Contract.class);
//        ContractDTO mockContractDTO = mock(ContractDTO.class);
//
//        LocalDate signatureDate = LocalDate.now();
//        Signature<Manager> expectedSignature = new Signature<>(mockManager, signatureDate);
//
//        when(userRepository.findById(managerId)).thenReturn(Optional.of(mockManager));
//        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
//        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
//        when(mockContract.toDTO()).thenReturn(mockContractDTO);
//
//        Optional<ContractDTO> result = contractManipulator.signContract(managerId, contractId);
//
//        assertThat(result).isPresent();
//        verify(mockContract).setManagerSignature(expectedSignature);
//        verify(contractRepository).save(mockContract);
//    }


//    @Test
//    public void signContract_ContractNotFound() {
//        Long contractId = 1L;
//        Student student = mock(Student.class);
//
//        when(userRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> contractManipulator.signContract(student.getId(), contractId))
//                .isInstanceOf(NoSuchElementException.class)
//                .hasMessageContaining("Contract not found");
//    }

//    @Test
//    public void signContract_NullUserId() {
//        Long contractId = 1L;
//
//        assertThatThrownBy(() -> contractManipulator.signContract(null, contractId))
//                .isInstanceOf(NoSuchElementException.class)
//                .hasMessageContaining("User not found");
//    }

}
