package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.UserDBO;

@Data
public class FriendCountDTO {

    private Integer followCount;

    private Integer fansCount;

}
