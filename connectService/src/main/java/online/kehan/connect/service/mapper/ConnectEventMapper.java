package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.ConnectEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConnectEvent} and its DTO {@link ConnectEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectEventMapper extends EntityMapper<ConnectEventDTO, ConnectEvent> {



    default ConnectEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConnectEvent connectEvent = new ConnectEvent();
        connectEvent.setId(id);
        return connectEvent;
    }
}
