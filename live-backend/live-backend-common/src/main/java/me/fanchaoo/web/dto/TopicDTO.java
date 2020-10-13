package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.CommentDBO;
import me.fanchaoo.dbo.TopicDBO;

@Data
public class TopicDTO extends TopicDBO {

    private String createUserName;

    private String lastUpdateUserName;

    private Integer momentCount;

    private Integer userCount;

}
