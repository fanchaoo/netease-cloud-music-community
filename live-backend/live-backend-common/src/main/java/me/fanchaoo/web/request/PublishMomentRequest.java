package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumMomentType;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PublishMomentRequest {

    @NotNull
    private EnumMomentType momentType;

    @Length(max = 250)
    private String textContent;

    @Size(max = 9)
    @Valid
    private List<Image> imageList;

    @Valid
    private Video video;

    @Size(min = 0, max = 5)
    private List<Long> tagIdList;

    private Long activityId;

    ////////////////////////////////////////////////////////////////////////

    @Data
    public static class Video {

        @NotBlank
        @Length(max = 30)
        private String title;

        @NotBlank
        @Length(max = 255)
        private String url;

        @NotNull
        private Integer width;

        @NotNull
        private Integer height;

        @NotBlank
        @Length(max = 255)
        private String coverUrl;

        @NotNull
        private Integer coverWidth;

        @NotNull
        private Integer coverHeight;
    }

    @Data
    public static class Image {
        @NotBlank
        @Length(max = 255)
        private String url;

        @NotNull
        private Integer width;

        @NotNull
        private Integer height;
    }
}
