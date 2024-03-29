package com.equipe4.audace.dto.notification;

import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.notification.Notification;
import com.equipe4.audace.model.notification.NotificationOffer;

public class NotificationOfferDTO extends NotificationDTO {
    public OfferDTO offer;

    public NotificationOfferDTO(
            Long id,
            UserDTO userDTO,
            Notification.NotificationCause cause,
            OfferDTO content
    ) {
        super(id, userDTO, cause);
        this.offer = content;
    }

    public NotificationOffer fromDTO() {
        return new NotificationOffer(id, user.fromDTO(), cause, offer.fromDTO());
    }
}
