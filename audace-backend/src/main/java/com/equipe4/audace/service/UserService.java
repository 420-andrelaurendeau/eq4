package com.equipe4.audace.service;

import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
@Data
public class UserService {
    protected final OfferRepository offerRepository;
    protected final DepartmentRepository departmentRepository;
}
