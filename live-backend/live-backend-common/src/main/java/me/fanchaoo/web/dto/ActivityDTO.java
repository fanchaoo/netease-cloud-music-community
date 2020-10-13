package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.ActivityDBO;

@Data
public class ActivityDTO extends ActivityDBO {

    private Boolean hasJoined;
}
