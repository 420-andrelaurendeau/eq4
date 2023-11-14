package com.equipe4.audace.utils;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.*;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    public void signContract_Student() {
        Long contractId = 1L;
        Student mockStudent = mock(Student.class);
        Contract mockContract = mock(Contract.class);
        ContractDTO mockContractDTO = mock(ContractDTO.class);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
        when(mockContract.toDTO()).thenReturn(mockContractDTO);

        Optional<ContractDTO> result = contractManipulator.signContract(mockStudent, contractId);

        assertThat(result).isPresent();
        verify(contractRepository).save(any(Contract.class));
    }

    @Test
    public void signContract_Employer() {
        Long contractId = 1L;
        Employer mockEmployer = mock(Employer.class);
        Contract mockContract = mock(Contract.class);
        ContractDTO mockContractDTO = mock(ContractDTO.class);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
        when(mockContract.toDTO()).thenReturn(mockContractDTO);

        Optional<ContractDTO> result = contractManipulator.signContract(mockEmployer, contractId);

        assertThat(result).isPresent();
        verify(contractRepository).save(any(Contract.class));
    }

    @Test
    public void signContract_Manager() {
        Long contractId = 1L;
        Manager mockManager = mock(Manager.class);
        Contract mockContract = mock(Contract.class);
        ContractDTO mockContractDTO = mock(ContractDTO.class);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));
        when(contractRepository.save(any(Contract.class))).thenReturn(mockContract);
        when(mockContract.toDTO()).thenReturn(mockContractDTO);

        Optional<ContractDTO> result = contractManipulator.signContract(mockManager, contractId);

        assertThat(result).isPresent();
        verify(contractRepository).save(any(Contract.class));
    }

    @Test
    public void signContract_ContractNotFound() {
        Long contractId = 1L;
        Student student = new Student();
        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractManipulator.signContract(student, contractId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Contract not found");
    }

    @Test
    public void signContract_NullUser() {
        Long contractId = 1L;
        Contract mockContract = mock(Contract.class);
        when(contractRepository.findById(contractId)).thenReturn(Optional.of(mockContract));

        assertThatThrownBy(() -> contractManipulator.signContract(null, contractId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
