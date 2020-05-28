package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.ConnectStateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConnectState} and its DTO {@link ConnectStateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectStateMapper extends EntityMapper<ConnectStateDTO, ConnectState> {



    default ConnectState fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConnectState connectState = new ConnectState();
        connectState.setId(id);
        return connectState;
    }
}
