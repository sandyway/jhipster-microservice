package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.RecipientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recipient} and its DTO {@link RecipientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipientMapper extends EntityMapper<RecipientDTO, Recipient> {



    default Recipient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipient recipient = new Recipient();
        recipient.setId(id);
        return recipient;
    }
}
