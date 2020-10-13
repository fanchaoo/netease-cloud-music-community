package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.MomentDBO;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class RepostUserDTO {

    private Long userId;

    private String userName;

    private String userAvatarUrl;

    private DateTime repostTime;

    private String textContent;
}
