package com.equipe4.audace.repository.signature;

import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.model.contract.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignatureRepository extends JpaRepository<Signature<?>, Long> {
    public List<Signature<?>> findAllByContract(Contract contract);
}
