package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.UserDBO;
import org.joda.time.DateTime;

@Data
public class UserDTO extends UserDBO {

    private Boolean hasFollowed;
}
