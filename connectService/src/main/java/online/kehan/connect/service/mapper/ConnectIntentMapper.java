package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.ConnectIntentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConnectIntent} and its DTO {@link ConnectIntentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectIntentMapper extends EntityMapper<ConnectIntentDTO, ConnectIntent> {



    default ConnectIntent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConnectIntent connectIntent = new ConnectIntent();
        connectIntent.setId(id);
        return connectIntent;
    }
}
