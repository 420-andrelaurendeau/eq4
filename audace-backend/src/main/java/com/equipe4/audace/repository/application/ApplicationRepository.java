package com.equipe4.audace.repository.application;

import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByOffer(Offer offer);
}
