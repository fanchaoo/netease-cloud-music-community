package me.fanchaoo.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JoinedActivityRequest {

    @NotNull
    private Long activityId;

    @NotNull
    private Boolean hasJoined;
}
