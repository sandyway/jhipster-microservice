package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.ConnectConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConnectConfig} and its DTO {@link ConnectConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectConfigMapper extends EntityMapper<ConnectConfigDTO, ConnectConfig> {



    default ConnectConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConnectConfig connectConfig = new ConnectConfig();
        connectConfig.setId(id);
        return connectConfig;
    }
}
