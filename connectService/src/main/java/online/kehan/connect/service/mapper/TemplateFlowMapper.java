package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.TemplateFlowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateFlow} and its DTO {@link TemplateFlowDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateFlowMapper extends EntityMapper<TemplateFlowDTO, TemplateFlow> {



    default TemplateFlow fromId(Long id) {
        if (id == null) {
            return null;
        }
        TemplateFlow templateFlow = new TemplateFlow();
        templateFlow.setId(id);
        return templateFlow;
    }
}
