package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.ReminderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reminder} and its DTO {@link ReminderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReminderMapper extends EntityMapper<ReminderDTO, Reminder> {



    default Reminder fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reminder reminder = new Reminder();
        reminder.setId(id);
        return reminder;
    }
}
