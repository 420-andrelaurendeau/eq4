package com.equipe4.audace.model.notification;

import com.equipe4.audace.dto.notification.NotificationOfferDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.model.offer.Offer;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class NotificationOffer extends Notification {
    public NotificationOffer(Long id, User user, NotificationCause cause, Offer content) {
        super(id, user, cause);
        this.content = content;
    }
    @ManyToOne
    private Offer content;

    public NotificationOfferDTO toDTO() {
        return new NotificationOfferDTO(
                id,
                user.toDTO(),
                cause,
                content.toDTO()
        );
    }
}
