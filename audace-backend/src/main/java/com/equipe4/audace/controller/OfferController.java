package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private Logger logger = LoggerFactory.getLogger(OfferController.class);


    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDTO> getAllOffers(){
        logger.info("getAllOffers");
        return offerService.findAllOffers();
    }
    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        try {
            OfferDTO createdOffer = offerService.createOffer(offerDTO).get();
            return ResponseEntity.ok(createdOffer);
        } catch (IllegalArgumentException e){
            if (e.getMessage().equals("Offer cannot be null")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else if (e.getMessage().equals("Offer already exists")) return ResponseEntity.status(HttpStatus.CONFLICT).build();
            else if (e.getMessage().equals("Employer not found")) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            else if (e.getMessage().equals("Department not found")) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            else if (e.getMessage().equals("Offer is not valid")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else if (e.getMessage().equals("Offer dates are not valid")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        OfferDTO updatedOffer = offerService.updateOffer(offerDTO).get();
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping
    public ResponseEntity deleteOffer(@RequestParam("offerId") Long offerId){
        offerService.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }
}
