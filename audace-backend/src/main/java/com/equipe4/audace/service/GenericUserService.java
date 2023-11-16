package com.equipe4.audace.service;

import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class GenericUserService <T extends User> {
    protected final ApplicationRepository applicationRepository;
    protected final ContractRepository contractRepository;
    protected final SaltRepository saltRepository;

    protected void hashAndSaltPassword(T user) {
        String generatedSalt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), generatedSalt);

        user.setPassword(hashedPassword);
        saltRepository.save(new Salt(null, user, generatedSalt));
    }

    public Optional<ContractDTO> getContractByApplicationId(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));

        return contractRepository.findByApplication(application).map(Contract::toDTO);
    }
}
