package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
@Data
public class UserService {
    protected final OfferRepository offerRepository;
    protected final DepartmentRepository departmentRepository;

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(OfferDTO::new).toList();
    }
}
