package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.ActivityDBO;
import me.fanchaoo.dbo.NotificationDBO;

@Data
public class AtNotificationDTO extends NotificationDBO {

    private MomentDTO moment;
}
