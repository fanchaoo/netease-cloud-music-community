package me.fanchaoo.web.dto;

import lombok.Data;
import me.fanchaoo.dbo.MomentDBO;

import java.util.List;

@Data
public class MomentDTO extends MomentDBO {

    private String userName;

    private String userAvatarUrl;

    private Boolean hasLiked;

    private Boolean hasFollowed;

    private List<MomentImageDTO> imageList;

    private MomentVideoDTO video;

    private MomentDTO repostedMoment;
}
