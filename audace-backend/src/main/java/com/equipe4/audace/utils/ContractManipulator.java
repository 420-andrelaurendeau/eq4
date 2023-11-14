package com.equipe4.audace.utils;

import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ContractManipulator {
    private final ContractRepository contractRepository;
    private final ApplicationRepository applicationRepository;

    public Optional<ContractDTO> getContractByApplicationId(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));

        return contractRepository.findByApplication(application).map(Contract::toDTO);
    }

    public Optional<ContractDTO> signContract(User user, Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new NoSuchElementException("Contract not found"));
        nullCheck(user);

        LocalDate signatureDate = LocalDate.now();

        if (user instanceof Student) {
            Signature<Student> studentSignature = new Signature<>((Student) user, signatureDate);
            contract.setStudentSignature(studentSignature);
        } else if (user instanceof Employer) {
            Signature<Employer> employerSignature = new Signature<>((Employer) user, signatureDate);
            contract.setEmployerSignature(employerSignature);
        } else if (user instanceof Manager) {
            Signature<Manager> managerSignature = new Signature<>((Manager) user, signatureDate);
            contract.setManagerSignature(managerSignature);
        } else {
            throw new IllegalArgumentException("Unsupported user type for signing");
        }

        return Optional.of(contractRepository.save(contract).toDTO());
    }

    private <T> void nullCheck(T object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}
